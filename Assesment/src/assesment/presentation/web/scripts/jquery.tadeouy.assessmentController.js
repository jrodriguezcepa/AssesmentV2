(function($) {

	$.widget('tadeouy.assessmentController', $.tadeouy.loggable, {
		options: {
			cardsNumbers: '.numbers',
			cardsPercent: '.percent'
		},

		_create: function(){
			var assessmentController = this;
			this.$assessmentController = this.element;
			this.assessment = this.options.assessment || this.$assessmentController.data('tadeouy-assessment');
			this.$assessment = this.assessment.$assessment;
			this.$cardsNumbers = $(this.options.cardsNumbers);
			this.$cardsPercent = $(this.options.cardsPercent);

			this.$assessment.bind('assessmentcardsinited', function($event, data){
				assessmentController._cardsInited($event, data);
			});

			this.$assessment.bind('assessmentcardshowed', function($event, data){
				assessmentController._cardShowed($event, data);
			});

			this.$assessment.bind('assessmentdatasent', function($event, data){
				assessmentController._updateNumbers($event, data);
			});

			this.$assessment.bind('assessmentresponsereceived', function($event, data){
				assessmentController._updateNumbers($event, data);
			});

			if (this.assessment.cards){
				this._cardShowed(null, {assessment: this.assessment});
			}
		},

		_cardsInited: function($event, data) {
		},

		_cardShowed: function($event, data) {
			this._updateData($event, data);
		},

		_updateData : function($event, data) {
			this._updateNumbers($event, data);
			this._updateImage($event, data);
		},

		_updateNumbers : function($event, data) {
			this.$cardsNumbers.html((data.assessment.currentCard.index+1) + '/' + data.assessment.cards.length);
			var waiting, completed, total, htmlText, titleText;
			waiting = data.assessment.getWaitingQuestions().length;
			completed = data.assessment.getCompletedQuestions().length;
			total = data.assessment.getQuestions().length;
			htmlText = Math.round(100 * completed / total) + '%';
			titleText = completed + '/' + total;
			if (waiting){
				htmlText = '<span class="loading">' + Math.round(100 * (waiting + completed) / total) + '%' + '</span>' + htmlText;
			}
			this.$cardsPercent.html(htmlText);
			this.$cardsPercent.attr('title', titleText);

		},

		_updateImage : function($event, data) {
			this.$assessmentController.find('img.picture').detach().hide();
			this.$assessmentController.append(data.assessment.currentCard.$picture);
			this.$assessmentController.find('img.picture').fadeIn();
		}

	});

})(jQuery);
