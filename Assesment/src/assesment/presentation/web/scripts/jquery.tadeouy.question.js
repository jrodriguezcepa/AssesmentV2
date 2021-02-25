(function($) {

	$.widget('tadeouy.question', $.tadeouy.loggable, {
		options: {
			create: function($event, data){
				$(this).data('tadeouy-question').log('tadeouy.question created');
			}
		},

		_create: function(){
			var question = this;
			this.$question = this.element;
			this.id = this._getId();
			this.questionType = this.$question.data('type');
			this.$input = this.$question.find('input, select, textarea');
			this.multiInputs = this.$input.length>1;
			this._initComplexQuestion();

			this.$error = $('<div class="error_text"></div>');
			this.$label = this.$question.find('label').eq(0);
			this.$label.after(this.$error);

			this.completed = this.$question.data('completed');
			this.valid = this.completed;

			if (this.completed) {
				this.$question.toggleClass('completed', this.completed);
				this.$question.toggleClass('valid', this.completed);
				this.$input.prop('disabled', true);
			}else{
				this.$input.bind('keyup change blur', function($event){
					question._validate();
				});
				this._validate();
			}
			if (this.questionType===15){
				this.addVideoToCard();
			}
			if (this.questionType===16){
				this.addSwfToCard();
			}
		},

		onPlayerStateChange: function(event) {
			if(event.data === 0) {
				this.videoEnded = true;
				this._validate();
			}
		},

		onPlayerStateChange2: function(event) {
			this.swfEnded = true;
			this._validate();
		},

		onPlayerReady: function(event) {
			// event.target.playVideo();
		},

		addVideoToCard: function() {
			var question = this;
			var playerId = 'player-' + this.id;
			var events = {
				'onReady': function(event){
					question.onPlayerReady(event);
				},
				'onStateChange': function(event){
					question.onPlayerStateChange(event);
				}
			};

			var $videoPlayer = $('<div id="' + playerId + '"></div>');
			this.$question.append($videoPlayer);
			if(playerId == 'player-44897') {
				this.player = this.getNoControlYouTubeVideo(playerId, {events: events});
			}else {
				this.player = this.getYouTubeVideo(playerId, {events: events});
			}
		},

		addSwfToCard: function() {
			var question = this;
			var playerId = 'player-' + this.id;
			var events = {
					'onplay': function(event){
						this.swfEnded = true;
						question.toggleClass('valid', true);
					}
				};
			var sourceMP4 = document.createElement("video"); 
			sourceMP4.src = "../assesment/videos/video_"+this.$question.data('video-id')+".mp4";
			sourceMP4.setAttribute("width", "640");
			sourceMP4.setAttribute("height", "270");
			sourceMP4.setAttribute("controls", "controls");
 			sourceMP4.addEventListener("ended", function(event){ 
				question.onPlayerStateChange2(event);
			});
			this.$question.append(sourceMP4, {events: events});
		},

		doPlay2: function(){
			this.swfEnded = true;
			this._validate();
		},
		
		swfCallback: function(){
			// called from inside swf through window.swfCallback
			this.swfEnded = true;
			this._validate();
		},

		getYouTubeVideo: function(playerId, customOptions) {
			var options = {
				width: '480',
				height: '270',
				videoId: this.$question.data('video-id'),
				playerVars: {
					'showinfo': 0, // removes title and share top bar
					'modestbranding': 1, // changes youtube logo in controls with watermark
					'color': 'white', // viewed bar color (disable modestbranding)
					'rel': 0, // removes related videos
					'hl': window.assessmentApp.lang, // set the language to match the app
					'playsinline': 1, // put an inline video viewer in ios instead of fullscreen
					'theme': 'light', // put an inline video viewer in ios instead of fullscreen
					// 'autoplay': 1, // autoplay video
					//'controls': 0, // removes video controls
					'final-semicolon-simplifier': 0
				},
				events: {}
			};
			options = $.extend(options, customOptions);
			var player = new YT.Player(playerId, options);
			return player;
		},

		getNoControlYouTubeVideo: function(playerId, customOptions) {
			var options = {
				width: '480',
				height: '270',
				videoId: this.$question.data('video-id'),
				playerVars: {
					'showinfo': 0, // removes title and share top bar
					'modestbranding': 1, // changes youtube logo in controls with watermark
					'color': 'white', // viewed bar color (disable modestbranding)
					'rel': 0, // removes related videos
					'hl': window.assessmentApp.lang, // set the language to match the app
					'playsinline': 1, // put an inline video viewer in ios instead of fullscreen
					'theme': 'light', // put an inline video viewer in ios instead of fullscreen
					// 'autoplay': 1, // autoplay video
					'controls': 0, // removes video controls
					'final-semicolon-simplifier': 0
				},
				events: {}
			};
			options = $.extend(options, customOptions);
			var player = new YT.Player(playerId, options);
			return player;
		},

		addVideoToContainer: function($container) {
			var playerId = 'small-player-' + this.id;
			var $videoPlayer = $('<div id="' + playerId + '"></div>');
			$container.append($videoPlayer);
			if(playerId == 'player-44897') {
				var videoPlayer = this.getNoControlYouTubeVideo(playerId, {
					width: '380',
					height: '214'
				});
			}else {
				var videoPlayer = this.getYouTubeVideo(playerId, {
					width: '380',
					height: '214'
				});
			}
		},

		getAnswer: function() {
			var answerObject = {
				id: this.id,
				answer: this.getValue()
			};
			return answerObject;
		},

		waitingAnswer: function(direction){
			this.waiting = direction;
		},

		serverResponse: function(valid, error){
			this.waitingAnswer(false);
			this.completed = valid;
			this.$question.toggleClass('completed', valid);
			this.$question.toggleClass('error', !valid);
			this.showErrorText(error);
		},

		showErrorText: function(error){
			this.$error.text(error || '');
		},

		focus: function(){
			this.$input.first().focus();
		},

		autoConfirmType: function(){
			var autoConfirmTypes = [3, 4];
			return autoConfirmTypes.indexOf(this.questionType) !== -1;
		},

		show: function(direction){
			this.$input.prop('disabled', !direction);
			if (this.questionType===6){
				// distance with units
				this.$unitsSelect.prop('disabled', !direction);
			}else if (this.questionType===7 || this.questionType===71){
				// date with 'never'
				this.$neverCheckbox.prop('disabled', !direction);
			}
		},

		getValue: function() {
			if (this.multiInputs){
				var type = this.$input.attr('type');
				var $checked = this.$input.filter(':checked');
				if (type==='checkbox'){
					var values = [];
					$checked.each(function(index, element){
						values.push($(element).val());
					});
					return values;
				}else if (type==='radio'){
					if (this.questionType===13){
						return this._getValueType13();
					}else if (this.questionType===14){
						return this._getValueType14();
					}else{
						return $checked.val();
					}
				}
			}else{
				if (this.questionType===6){
					return this.$input.val() + this.$unitsSelect.val();
				}else if (this.questionType===2){
					// distance with units
					return this._getValueType2();
				}else if (this.questionType===7 || this.questionType===71){
					return this._getValueType7();
				}else if (this.questionType===15){
					return this.videoEnded;
				}else if (this.questionType===16){
					return this.swfEnded;
				}else{
					return this.$input.val();
				}
			}
		},

		_getId: function() {
			return this.$question.attr('id').slice(2);
		},

		_hasError: function() {
			return this.$question.hasClass('error');
		},

		_validate: function(){
			this.valid = this._isValid();
			if (this.valid) {
				this.$question.toggleClass('error', false);
				this.showErrorText();
			}
			this._trigger('validated', null, {question: this, $question: this.$question});
		},

		_initComplexQuestion: function(){
			var question = this;
			var initialValue = this.$input.attr('value');
			if (this.questionType===6){
				// distance with units
				var miles = {vt:'miles', in:'miles', id:'miles', ph:'miles', pk:'miles', 'en': 'miles', 'es': 'millas', 'pt': 'milhas'}[window.assessmentApp.lang];
				var kmts = {vt:'kilometers', in:'kilometers', id:'kilometers', ph:'kilometers', pk:'','en': 'kilometers', 'es': 'kil&oacute;metros', 'pt': 'quil&ocirc;metros'}[window.assessmentApp.lang];
				this.$unitsSelect = $('<select class="units"><option value="k">' + kmts + '</option><option value="m">' + miles + '</option></select>');
				this.$input.after(this.$unitsSelect);
				if(initialValue){
					this.$unitsSelect.val(initialValue.slice(-1));
					this.$input.val(initialValue.slice(0,-1));
				}else{
					if(window.assessmentApp.lang === 'en'){
						this.$unitsSelect.val('m');
					}
				}
			}else if (this.questionType===7){
				// date with 'never'
				var never = {'vt':'Never', 'in':'Never', 'id':'Never', 'ph':'Never', 'pk':'Never','en': 'Not Available - Do not have license', 'es': 'No Aplica - No tengo licencia de conducir', 'pt': 'Nao Aplica - Nao tenho carteira'}[window.assessmentApp.lang];
				var $neverLabel = $('<label class="checkbox"><input class="never" type="checkbox">' + never + '</label>');
				this.$neverCheckbox = $neverLabel.find('input.never');
				this.$neverCheckbox.on('change', function($event, element){
					question._changeNeverCheckbox($event, element);
				});
				this.$input.after($neverLabel);
				if(initialValue === '0'){
					this.$input.val('');
					this.$neverCheckbox.attr('checked', true).change();
				}
			}else if (this.questionType===71){
				// date with 'never'
				var never = {'en': 'No tengo licencia de conducir', 'es': 'No tengo licencia de conducir', 'pt': 'No tengo licencia de conducir'}[window.assessmentApp.lang];
				var $neverLabel = $('<label class="checkbox"><input class="never" type="checkbox">' + never + '</label>');
				this.$neverCheckbox = $neverLabel.find('input.never');
				this.$neverCheckbox.on('change', function($event, element){
					question._changeNeverCheckbox($event, element);
				});
				this.$input.after($neverLabel);
				if(initialValue === '0'){
					this.$input.val('');
					this.$neverCheckbox.attr('checked', true).change();
				}
			}
		},

		_isValid: function(){
			var valid;
			if (this.multiInputs){
				var type = this.$input.attr('type');
				if (type==='checkbox'){
					valid = true;
				}else if (type==='radio'){
					valid = this.$input.is(":checked");
				}
			}else{
				switch (this.questionType) {
					case 9:
						valid = this._validateEmail(this.$input.val());
						break;
					case 7:case 71:
						valid = !!this._getValueType7();
						break;
					case 15:
						valid = this.videoEnded;
						break;
					case 16:
						valid = this.swfEnded;
						break;
					default:
						valid = !!this.$input.val();
				}
			}
			this.$question.toggleClass('valid', valid);
			return valid;
		},

		_getValueType2: function(){
			var plainValue = this.$input.val();
			return this._getIsoDateString(plainValue);
		},

		_getValueType7: function(){
			var never = this.$neverCheckbox.is(":checked");
			if (never){
				return '0';
			}else{
				var plainValue = this.$input.val();
				return this._getIsoDateString(plainValue);
			}
		},

		isNoSelected: function(){
			var $radioButtons = this.$input.filter('[type="radio"]');
			return ($radioButtons.eq(0).filter(':checked').length === 0 && $radioButtons.eq(1).filter(':checked').length === 1);
		},

		_getValueType13: function(){
			var $checked = this.$input.filter(':checked');
			var checkboxValue = $checked.val();
			var textValue = this.$input.filter('[type="text"]').val();
			return checkboxValue + '-' + textValue;
		},

		_getValueType14: function(){
			var $checked = this.$input.filter(':checked');
			var checkboxValue = $checked.val();
			var plainDateValue = this.$input.filter('[type="text"],[type="date"]').val();
			return checkboxValue + '-' + this._getIsoDateString(plainDateValue);
		},

		_getIsoDateString: function(plainValue){
			var day,month,year,isoDateString;
			var humanDateFormatParts = plainValue.split('/');
			if (humanDateFormatParts.length === 3){
				year = humanDateFormatParts[2];
				if (window.assessmentApp.lang === 'en'){
					month = humanDateFormatParts[0];
					day = humanDateFormatParts[1];
				}else{
					day = humanDateFormatParts[0];
					month = humanDateFormatParts[1];
				}
				isoDateString = year + '-' + month + '-' + day;
			}else{
				isoDateString = plainValue;
			}
			return isoDateString;
		},

		_changeNeverCheckbox: function($event, element){
			var never = this.$neverCheckbox.is(":checked");
			this.$input.prop('type', never ? 'text' : 'date');
			this.$input.val(never ? this.$neverCheckbox.parent().text() : '');
			this.$input.prop('disabled', never);
			this._validate();
		},

		_testComplete: function() {
			var testValidValue = this._getTestValidValue();
			if (this.questionType===15){
				this.videoEnded = true;
			}else if (this.questionType===16){
				this.swfEnded = true;
			}else{
				this.$input.filter(':not("[type="checkbox"],[type="radio"]")').val(testValidValue);
			}
			this._validate();
		},

		_getTestValidValue: function() {
			var testValidValue = 'Test';
			if (this.questionType===2 || this.questionType===7|| this.questionType===71 || this.questionType===12 || this.questionType===14){
				testValidValue = {vt:'10/31/1976', in:'10/31/1976', id:'10/31/1976', ph:'10/31/1976', pk:'10/31/1976', 'en': '10/31/1976', 'es': '31/10/1976', 'pt': '31/10/1976'}[window.assessmentApp.lang];
			}
			if (this.questionType===3 || this.questionType===8 || this.questionType===13 || this.questionType===14){
				this.$input.eq(0).attr('checked', true);
			}else if (this.questionType===4 || this.questionType===10){
				// select
				testValidValue = this.$input.find('option').eq(1).attr('value');
			}else if (this.questionType===6){
				// distance with units
				testValidValue = 100;
			}else if (this.questionType===9){
				testValidValue = 'test@server.com';
			}else if (this.questionType===15){
				testValidValue = 'viewed';
			}
			return testValidValue;
		},

		_validateEmail: function(email){
			var re = /^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])$/;
			return re.test(email);
		}

	});

})(jQuery);
