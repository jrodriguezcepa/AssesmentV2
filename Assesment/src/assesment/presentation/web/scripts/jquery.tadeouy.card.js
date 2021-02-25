(function($) {

	$.widget('tadeouy.card', $.tadeouy.loggable, {
		options: {
			picture: '.picture',
			autoFocus: true,
			create: function($event, data){
				$(this).data('tadeouy-card').log('tadeouy.card created');
			},
			show: function($event, data){
				var card = $(this).data('tadeouy-card');
				if (data.direction){
					card.$card.fadeIn();
				}else{
					card.$card.fadeOut();
				}
			}
		},

		_create: function(){
			var card = this;
			this.$card = this.element;
			this.$picture = this.$card.find(this.options.picture).detach().hide();
			this.$questions = this.$card.find(this.options.questionSelector);
			this.no_questions = this.$questions.length === 0;

			this.$confirmButton = $(window.assessmentApp.parseTemplate(this.options.confirmButtonTag, {label: {'vt': 'Continue', in:'Continue', id:'Continue', ph:'Continue', pk:'Continue', 'en': 'Continue', 'es': 'Continuar', 'pt': 'Continuar'}[window.assessmentApp.lang]}));
			this.$card.append(this.$confirmButton);
			this.$confirmButton = this.$confirmButton.add(this.$card.find('.confirm'));
			this.$confirmButton.bind('click', function($event){
				card.tryToSubmit();
				return false;
			});

			this.index = this.options.index;

			this.waitingAnswer = false;
			this.assessment = this.options.assessment;
			this.questions = [];

			if (this.index!==0) {
				this.$previousButton = $(window.assessmentApp.parseTemplate(this.options.previousButtonTag, {label: {'vt': 'Previous', in:'Previous', id:'Previous', ph:'Previous', pk:'Previous', 'en': 'Previous', 'es': 'Anterior', 'pt': 'Anterior'}[window.assessmentApp.lang]}));
				this.$card.append(this.$previousButton);
				this.$previousButton.bind('click', function($event){
					card.assessment.advanceCard(-1);
					return false;
				});
			}

			this.$card.append('<span class="card_number">' + (this.index+1) + '</span>');

			this.element.bind('cardshow', this.options.show);
			this._initQuestions();
			this.show(false);
		},

		_initQuestions: function(){
			this.log('tadeouy.card _initQuestions');
			var card = this;
			this.$questions.each(function(index){
				var $this = $(this);
				var questionType = $this.data('type');
				$this.question();
				var question = $this.data('tadeouy-question');
				card.questions.push(question);
				$this.bind('questionvalidated', function($event, data){
					card._validate($event, data);
				});
			});
			this._trigger('questionsinited', null, {'$questions': this.$questions, 'card': this});
		},

		_validate: function($event, data){
			if(this.$card.prop('disabled')){
				return;
			}
			this.valid = this._isValid();
			this.$confirmButton.attr('disabled', this.valid ? null : 'disabled');

			if(this.valid && data && data.question===this.questions[this.questions.length-1] && data.question.autoConfirmType()){
				this.tryToSubmit();
			}
		},

		_isValid: function(){
			for (var i = 0; i < this.questions.length; i++) {
				if (!this.questions[i].valid){
					return false;
				}
			}
			return true;
		},

		getConfirmQuestions: function(){
			var questions = [];
			for (var questionFor, i = 0; i < this.questions.length; i++) {
				questionFor = this.questions[i];
				if (questionFor.questionType === 13 || questionFor.questionType === 14){
					if (questionFor.isNoSelected()){
						questions.push(questionFor);
					}
				}
			}
			return questions;
		},

		getConfirmQuestionsText: function(questions){
			var questionsText = '<ul>';
			for (var i = 0; i < questions.length; i++) {
				questionsText += '<li>' + questions[i].$label.text() + '</li>';
			}
			questionsText += '</ul>';
			return questionsText;
		},

		confirmAnswers: function(){
			var card = this;
			if (window.assessmentApp.checklist){
				var confirmQuestions = this.getConfirmQuestions();
				if (confirmQuestions.length) {
					var confirmMessage = $('#confirm_message').html();
					confirmMessage = window.assessmentApp.parseTemplate(confirmMessage, {questions: this.getConfirmQuestionsText(confirmQuestions)});
					window.assessmentApp.$overlay.showContent(confirmMessage, true);
					window.assessmentApp.$overlay.find('.button.confirm_answers_ok').click(function(){
						card.tryToSubmit(true);
					});
					window.assessmentApp.$overlay.find('.button.confirm_answers_no').click(function(){
						window.assessmentApp.$overlay.showContent();
					});
				}else{
					this.tryToSubmit(true);
				}
			}else{
				this.tryToSubmit(true);
			}
		},

		tryToSubmit: function(confirmed){
			if (!confirmed){
				this.confirmAnswers();
				return;
			}
			if (this.waitingAnswer || !this.valid) return;
			this.assessment.continueButton();
			if (this.questions.length) {
				this.sendAnswers();
			}else{
				this.already_read = true;
			}
		},

		getFilteredQuestions: function(filterFunction){
			var filteredQuestions = [];
			for (var questionFor, i = 0; i < this.questions.length; i++) {
				questionFor = this.questions[i];
				if (filterFunction(questionFor)) {
					filteredQuestions.push(questionFor);
				}
			}
			return filteredQuestions;
		},

		getCompletedQuestions: function(){
			var completedQuestions = this.getFilteredQuestions(function(question){
				return question.completed;
			});
			return completedQuestions;
		},

		getErrorQuestions: function(){
			var errorQuestions = this.getFilteredQuestions(function(question){
				return question._hasError();
			});
			return errorQuestions;
		},

		getWaitingQuestions: function(){
			var waitingQuestions = this.getFilteredQuestions(function(question){
				return question.waiting;
			});
			return waitingQuestions;
		},

		isCompleted: function(){
			if (this.questions.length) {
				return this.getCompletedQuestions().length === this.questions.length;
			}else{
				return this.already_read;
			}
		},

		getQuestionWithId: function(id){
			for (var questionFor, i = 0; i < this.questions.length; i++) {
				questionFor = this.questions[i];
				if (questionFor.id === id) {
					return questionFor;
				}
			}
		},

		sendAnswers: function(){
			this.waitForAnswer(true);
			var url = this.assessment.postURL;
			// var url = 'ajax/server_response_' + (this.index+1) + '.json'; // for testing purpose
			// var url = 'ajax/server_response_' + (this.index+1) + '_error.json'; // for testing purpose
			// var url = 'ajax/server_response.checklist.json'; // for testing purpose
			var card = this;
			var data = this.getAnswers();
			data = encodeURIComponent(JSON.stringify(data));
			var jqxhr = $.ajax(url, {
				type: 'POST',
				data: data,
				dataType: 'json',
				context: card
			})
			.done(card._successResponse)
			.fail(card._errorResponse)
			.always(card._responseReceived);
			this.assessment.dataSent(this);
		},

		_successResponse: function(data, textStatus, jqXHR){
			var errorFor, validsFor, questionFor, i;
			if(data.valids && data.valids.length){
				for (i = 0; i < data.valids.length; i++) {
					validFor = data.valids[i];
					questionFor = this.getQuestionWithId(validFor);
					if(questionFor){
						questionFor.serverResponse(true);
					}
				}
			}
			if(data.errors && data.errors.length){
				for (i = 0; i < data.errors.length; i++) {
					errorFor = data.errors[i];
					questionFor = this.getQuestionWithId(errorFor.id);
					if(questionFor){
						questionFor.serverResponse(false, errorFor.error);
					}
				}
			}
		},

		_errorResponse: function(jqXHR, textStatus, errorThrown){
			if (typeof(console)!=='undefined') console.debug('_errorResponse', jqXHR, textStatus, errorThrown);
			if(window.assessmentApp.lang == 'es') {
				alert('Error en la conexión a Internet.\n\nPor seguridad se cerró su sesión, favor verificar su conexión e ingresar nuevamente con el mismo usuario y clave.');
			} else if(window.assessmentApp.lang == 'en') {
				alert('Connection error. Please exit the system. ');
			} else if(window.assessmentApp.lang == 'pt') {
				alert('Erro de conexão. Por favor, sair do sistema.');
			} else {
				alert('Connection error. Please exit the system. ');
			}
		},

		_responseReceived: function(data_or_jqXHR, textStatus, jqXHR_or_errorThrown) {
			this.waitForAnswer(false);
			this.assessment.responseReceived(this, data_or_jqXHR, textStatus, jqXHR_or_errorThrown);
		},

		waitForAnswer: function(direction) {
			this.waitingAnswer = direction;
			this.$confirmButton.each(function(index) {
				var $this = $(this);
				if (direction) {
					$this.data('normalLabel', $this.html());
					$this.html('Sending...');
				}else{
					$this.html($this.data('normalLabel'));
				}
				$this.attr('disabled', direction?'disabled':null);
			});
		},

		show: function(direction){
			this.$card.toggleClass('active', direction);
			this.$card.prop('disabled', !direction);
			for (var i = 0; i < this.questions.length; i++) {
				this.questions[i].show(direction);
			}
			if(direction){
				if(this.options.autoFocus){
					for (var i = 0; i < this.questions.length; i++) {
						if (!this.questions[i].completed){
							this.questions[i].focus();
							break;
						}
					}
				}
				this._validate();
			}else{
				this.$confirmButton.attr('disabled', 'disabled');
			}
			this._trigger('show', null, {'card': this, 'direction': direction});
		},

		load: function () {
			this.log('tadeouy.card load');
			var that = this;
		},

		getAnswers: function() {
			var answers = [];
			for (var questionFor, i = 0; i < this.questions.length; i++) {
				questionFor = this.questions[i];
				if (!questionFor.waiting && questionFor.valid){
					questionFor.waitingAnswer(true);
					answers.push(questionFor.getAnswer());
				}
			}
			var answersObject = {
				module: this.assessment.getModuleId(),
				answers: answers
			};
			return answersObject;
		},

		_testComplete: function() {
			for (var questionFor, i = 0; i < this.questions.length; i++) {
				questionFor = this.questions[i];
				questionFor._testComplete();
			}
		}

	});

})(jQuery);
