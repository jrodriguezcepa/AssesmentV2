window.mobilecheck = function() {
	var check = false;
	(function(a){if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino|android|ipad|playbook|silk/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4))) check = true})(navigator.userAgent||navigator.vendor||window.opera);
	return check;
};

if (!Array.prototype.indexOf) {
	Array.prototype.indexOf = function(val) {
		return jQuery.inArray(val, this);
	};
}

(function($){
	$(document).ready(function() {

		if ($('body').hasClass('login')) {
			var $fieldset = $('fieldset');
			$fieldset.find('a.switch').bind('click', function($event){
				showForm($(this).attr('href'));
			});
			var showForm = function (id){
				var $activeFieldset = $(id);
				$fieldset.hide();
				$activeFieldset.show();
				$activeFieldset.find('input').eq(0).focus();
			};
			showForm(window.location.hash || '#username_block');
			return;
		}


		var charset = $('meta[charset]').attr('charset');
		$.ajaxSetup({
			'beforeSend' : function(xhr) {
				xhr.overrideMimeType('text/html; charset=' + charset);
			},
		});

		window.assessmentApp = {
			lang: $('html').prop('lang') || 'es',
			parseTemplate: function(template, vars){
				var regexp;
				for (var i in vars){
					regexp = new RegExp('__'+i+'__', 'g');
					template = template.replace(regexp, vars[i]);
				}
				return template;
			}
		};

		var $assessment = $('#assessment');

		function initApp(){
			if ($assessment.length > 0){
				var $window = $(window);
				var top, minAbsTop, maxRelTop;
				var $navSections = $('nav.sections');
				var $offsetParent = $navSections.offsetParent();
				window.assessmentApp.initConstraintElevator = function() {
					minAbsTop = Number($navSections.data('min-rel-top'));
				};

				window.assessmentApp.updateElevatorPosition = function() {
					maxRelTop = $offsetParent.outerHeight() - $navSections.outerHeight() - Number($navSections.data('min-rel-bottom')) - minAbsTop;
					top = Math.min(maxRelTop, minAbsTop + $window.scrollTop());
					$navSections.css('top', top+'px');
				};
				assessmentApp.initConstraintElevator();
				var scrollTimer;
				var delayScroll = function(){
					if (scrollTimer) {
						clearInterval(scrollTimer);
					}
					scrollTimer = setTimeout(function(){
						assessmentApp.updateElevatorPosition();
					}, 50);
				};
				$window.on('scroll', function($event){
					assessmentApp.updateElevatorPosition();
				});
				$('nav.sections li').each(function(index){
					$(this).addClass('color_' + ((index % 6)+1));
				});
			}

			var checklist = $assessment.hasClass('checklist');
			window.assessmentApp.checklist = checklist;
			var confirmButtonTag = '<button class="button">__label__</button>';
			if (checklist){
				confirmButtonTag = window.assessmentApp.parseTemplate(confirmButtonTag, {label: {'vt': 'Submit', in:'Submit', id:'Submit', ph:'Submit', pk:'Submit', 'en': 'Submit', 'es': 'Enviar', 'pt': 'Enviar'}[window.assessmentApp.lang]});
			}
			$assessment.assessment({
				cardShowed: function($event, data){
					var $card = data.card.$card;
					$('html, body').stop().animate({
						scrollTop: $card.offset().top - 90
					}, 500, 'easeInOutCubic');
				},
				show: function(){
					return false;
				},
				cardSelector: checklist?'form.checklist':'fieldset',
				questionSelector: checklist?'fieldset:not(".no_question"):not(".title")>div':'>div:not(".no_question")',
				confirmButtonTag: confirmButtonTag,
				autoFocus: !window.mobilecheck()
			});

			var assessment = $assessment.data('tadeouy-assessment');
			assessmentApp.assessment = assessment;

			var $assessmentController = $('nav.sections');
			if ($assessmentController.assessmentController){
				$assessmentController.assessmentController({assessment: assessment});
			}

			if(window.mobilecheck()){
				$('fieldset div input[type="text"]').attr('autocorrect', 'off').attr('autocapitalize', 'off');
			}else{
				$.datepicker.setDefaults($.datepicker.regional[{vt:'', en:'', in:'', id:'', ph:'', pk:'', es:'es', pt:'pt'}[window.assessmentApp.lang]]);
				$("fieldset > div input[type='date']").prop('id','').datepicker({changeMonth: true, changeYear: true, yearRange: '-80:+30'}).prop('type','text');
			}



			/************* start overlay *************/

			var closeLabel = {vt:'Close', in:'Close', id:'Close', ph:'Close', pk:'Close', en:'Close', es:'Cerrar', pt:'Fechar'}[window.assessmentApp.lang];
			var loadingText = {vt:'Loading', in:'Loading', id:'Loading', ph:'Loading', pk:'Loading', en:'Loading', es:'Cargando', pt:'Carregando'}[window.assessmentApp.lang];
			var $overlay = $('<div id="overlay"><div class="content"></div><div class="after_content"></div></div>').appendTo($('body'));
			if (window.assessmentApp){
				window.assessmentApp.$overlay = $overlay;
			}
			var $overlayClose = $overlay.find('.close');
			var $overlayContent = $overlay.find('.content');
			$overlay.hide();
			$overlay.loadContent = function(url){
				if (typeof(url)==='undefined'){
					$overlay.hide();
					$overlayContent.html('');
				}else{
					$overlayContent.html('<p>' + loadingText + '...</p>');
					$overlayContent.load(url);
					$('html, body').animate({
						scrollTop: 0
					}, 500, 'easeInOutCubic');
					$overlay.show();
				}
			};
			$overlay.showContent = function(text, hideCloseButton){
				if (text){
					$overlayContent.html(text);
					$('html, body').animate({
						scrollTop: 0
					}, 500, 'easeInOutCubic');
					$overlayClose.toggle(!hideCloseButton);
					$overlay.show();
				}else{
					$overlay.hide();
					$overlayContent.html('');
				}
			};
			$overlayClose.bind('click', function($event) {
				$event.preventDefault();
				$overlay.loadContent();
			});
			$('a[target="_overlay"]').bind('click', function($event) {
				$event.preventDefault();
				var $this = $(this);
				var url = $this.attr('href');
				$overlay.loadContent(url);
			});

			/************* end overlay *************/


			$(window).on('keydown', function(event){
				if (event.metaKey && event.shiftKey){
					switch (event.keyCode) {
						case 85: case 69:
							assessmentApp.assessment.currentCard._testComplete();
							break;
					}
				}
			});
		}

		function addScriptTag(url) {
			var tag = document.createElement('script');
			tag.src = url;
			var firstScriptTag = document.getElementsByTagName('script')[0];
			firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
			return tag;
		}

		var flashPlayers = $assessment.find('fieldset [data-type="16"]');
		if (flashPlayers.length) {
			addScriptTag('scripts/swfobject.js');
			window.swfCallback = function (id) {
				$('#qw' + id).data('tadeouy-question').swfCallback();
			};
		}
		var videoPlayers = $assessment.find('fieldset [data-type="15"]');
		if (videoPlayers.length) {
			window.onYouTubeIframeAPIReady = function(){
				initApp();
			};
			addScriptTag('scripts/iframe_api.js');
			// addScriptTag('https://www.youtube.com/iframe_api');
		}else{
			initApp();
		}
	});
})(jQuery);
