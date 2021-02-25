(function($) {

	var constraintDeltaOnArray = function(position, delta, length){
		var unconstraintedPosition = position + delta;
		var normalizedPosition = unconstraintedPosition % length;
		return normalizedPosition<0 ? length + normalizedPosition : normalizedPosition;
	};

	$.widget('tadeouy.assessment', $.tadeouy.loggable, {
		options: {
			cardSelector: 'fieldset',
			questionSelector: '>div:not(".no_question")',
			confirmButtonTag: '<button class="button">__label__</button>',
			previousButtonTag: '<button class="button previous">__label__</button>',
			autoFocus: true,
			create: function(){
				$(this).data('tadeouy-assessment').log('tadeouy.assessment created');
			},
			cardsInited: function(){
				$(this).data('tadeouy-assessment').log('tadeouy.assessment cardsInited');
			},
			cardShowed: function(){
				$(this).data('tadeouy-assessment').log('tadeouy.assessment cardShowed');
			},
			questionValidated: function($event, data){
				$(this).data('tadeouy-assessment')._trigger('questionvalidated', $event, data);
				$(this).data('tadeouy-assessment').log('tadeouy.assessment questionValidated');
			}
		},

		_create: function(){
			var assessment = this;
			this.$assessment = this.element;

			this.postURL = this.$assessment.attr('action');

			this.$assessment.find('input[type="submit"]').hide();

			this.$cards = $(this.options.cardSelector);
			this.$panel = $(this.options.panel);

			this.$assessment.bind('submit', function($event, element){
				$event.preventDefault();
				assessment.currentCard.tryToSubmit();
			});

			this.element.bind('assessmentcardsinited', this.options.cardsInited);
			this.element.bind('assessmentcardshowed', this.options.cardShowed);
			this.element.bind('questionvalidated', this.options.questionValidated);

			this.cardToShow = null;
			this.currentCard = null;
			this.cards = [];

			this._initCards();
			this.gotoCard(this._getFirstUncompletedCard());
		},

		advanceCard: function(delta){
			this.log('tadeouy.assessment advanceCard');
			delta = delta || 1;
			constraintedIndex = constraintDeltaOnArray(this.currentCard.index, delta, this.cards.length);
			var nextCard = this.cards[constraintedIndex];
			this.gotoCard(nextCard);
		},

		continueButton: function(){
			this.log('tadeouy.assessment continueButton');
			var firstErrorCard = this.getFirstErrorCard();
			if (firstErrorCard){
				this.gotoCard(firstErrorCard);
			}else{
				var firstUncompletedCard = this._getFirstUncompletedCard(this.currentCard);
				if (firstUncompletedCard){
					this.gotoCard(firstUncompletedCard);
				}
			}
		},

		getFirstErrorCard: function(){
			this.log('tadeouy.assessment goFirstErrorCard');
			var errorQuestions = this.getErrorQuestions();
			if (errorQuestions.length){
				var firstErrorCard = errorQuestions[0].$question.parent().data('tadeouy-card');
				return firstErrorCard;
			}else{
				return false;
			}
		},

		gotoCard: function(card){
			card = this._getCardObject(card);
			this.log('tadeouy.assessment gotoCard: ' + card.index);
			this.showCard(card);
		},

		showCard: function(card){
			var prevCurrentCard;
			card = this._getCardObject(card);
			this.log('tadeouy.assessment showCard: ' + card.index);
			if (card === this.currentCard) return;
			if (this.currentCard){
				this.currentCard.show(false);
			}
			prevCurrentCard = this.currentCard;
			this.currentCard = card;
			this.currentCard.show(true);
			this._trigger('cardshowed', null, {'card': card, 'prevCard': prevCurrentCard, 'assessment': this});
		},

		getModuleId: function(){
			return this.$assessment.data('module');
		},

		collectCardsData: function(collectFunction){
			var collectedData = [];
			for (var cardFor, i = 0; i < this.cards.length; i++) {
				cardFor = this.cards[i];
				collectedData = collectedData.concat(collectFunction(cardFor));
			}
			return collectedData;
		},

		getWaitingQuestions: function(){
			var waitingQuestions = this.collectCardsData(function(card){
				return card.getWaitingQuestions();
			});
			return waitingQuestions;
		},

		getCompletedQuestions: function(){
			var completedQuestions = this.collectCardsData(function(card){
				return card.getCompletedQuestions();
			});
			return completedQuestions;
		},

		getErrorQuestions: function(){
			var errorQuestions = this.collectCardsData(function(card){
				return card.getErrorQuestions();
			});
			return errorQuestions;
		},

		getQuestions: function(){
			var questions = this.collectCardsData(function(card){
				return card.questions;
			});
			return questions;
		},

		dataSent: function(card){
			this._trigger('datasent', null, {'assessment': this, 'card': card});
		},

		responseReceived: function(card, data_or_jqXHR, textStatus, jqXHR_or_errorThrown){
			this._trigger('responsereceived', null, {'assessment': this, 'card': card});
			var questions = this.getQuestions();
			var completedQuestions = this.getCompletedQuestions();
			if(questions.length === completedQuestions.length){
				if(textStatus==='success' && data_or_jqXHR.final_text) {
					window.assessmentApp.$overlay.showContent(data_or_jqXHR.final_text);
				}
				var redirectURL = this.$assessment.data('success-redirect');
				if (redirectURL){
					window.location.href = redirectURL;
				}
			}
		},

		_initCards: function(){
			this.log('tadeouy.assessment _initCards');
			var assessment = this;

			this.$cards.each(function(index){
				var $this = $(this);
				$this.card({
					questionSelector: assessment.options.questionSelector,
					confirmButtonTag: assessment.options.confirmButtonTag,
					previousButtonTag: assessment.options.previousButtonTag,
					index: index,
					show: assessment.options.show,
					autoFocus: assessment.options.autoFocus,
					assessment: assessment
				});
				var card = $this.data('tadeouy-card');
				assessment.cards.push(card);
			});
			this._trigger('cardsinited', null, {'$cards': this.$cards, 'assessment': this});
		},

		_getFirstUncompletedCard: function(initialCard){
			var initialIndex = 0;
			if (typeof(initialCard)!=='undefined'){
				initialCard = this._getCardObject(initialCard);
				initialIndex = initialCard.index + 1;
			}
			for (var indexFor, i = 0; i < this.cards.length; i++) {
				indexFor = initialIndex + i;
				indexFor = indexFor % this.cards.length;
				cardFor = this.cards[indexFor];
				if (!cardFor.isCompleted()){
					return cardFor;
				}
			}
			return false;
		},

		_getCardObject: function(card){
			if (typeof(card) === 'undefined'){
				card = 0;
			}
			if (typeof(card) === 'number'){
				card = this.cards[card];
			}
			return card;
		}

	});
})(jQuery);
