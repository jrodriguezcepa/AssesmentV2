/**
 * @preserve IntegraXor Web SCADA - JavaScript Number Formatter
 * http://www.integraxor.com/
 * author: KPL, KHL
 * (c)2011 ecava
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */

////////////////////////////////////////////////////////////////////////////////
// param: Mask & Value
////////////////////////////////////////////////////////////////////////////////

window['format'] = function(v){
	var m="###,###,###.##";
    if (!m || isNaN(+v)) {
        return v; //return as it is.
    }
    
    if(v==0 || v=='0'){
    	return v;
    }
    
    if(v>0 && v < 1){
    	v=(v+"").replace(",",".");
    	if(v.indexOf(".")+2<v.length){
    		v=v.substring(0,v.indexOf(".")+2);	
    	}
    	return v;
    }
    //convert any string to number according to formation sign.
    var v = m.charAt(0) == '-'? -v: +v;
    var isNegative = v<0? v= -v: 0; //process only abs(), and turn on flag.
    
    //search for separator for grp & decimal, anything not digit, not +/- sign, not #.
    var result = m.match(/[^\d\-\+#]/g);
    var Decimal = (result && result[result.length-1]) || '.'; //treat the right most symbol as decimal 
    var Group = (result && result[1] && result[0]) || ',';  //treat the left most symbol as group separator
    
    //split the decimal for the format string if any.
    var m = m.split( Decimal);
    //Fix the decimal first, toFixed will auto fill trailing zero.
    v = v.toFixed( m[1] && m[1].length);
    v = +(v) + ''; //convert number to string to trim off *all* trailing decimal zero(es)

    //fill back any trailing zero according to format
    var pos_trail_zero = m[1] && m[1].lastIndexOf('0'); //look for last zero in format
    var part = v.split('.');
    //integer will get !part[1]
    if (!part[1] || part[1] && part[1].length <= pos_trail_zero) {
        v = (+v).toFixed( pos_trail_zero+1);
    }
    var szSep = m[0].split( Group); //look for separator
    m[0] = szSep.join(''); //join back without separator for counting the pos of any leading 0.

    var pos_lead_zero = m[0] && m[0].indexOf('0');
    if (pos_lead_zero > -1 ) {
        while (part[0].length < (m[0].length - pos_lead_zero)) {
            part[0] = '0' + part[0];
        }
    }
    else if (+part[0] == 0){
        part[0] = '';
    }
    
    v = v.split('.');
    v[0] = part[0];
    
    //process the first group separator from decimal (.) only, the rest ignore.
    //get the length of the last slice of split result.
    var pos_separator = ( szSep[1] && szSep[ szSep.length-1].length);
    if (pos_separator) {
        var integer = v[0];
        var str = '';
        var offset = integer.length % pos_separator;
        for (var i=0, l=integer.length; i<l; i++) { 
            
            str += integer.charAt(i); //ie6 only support charAt for sz.
            //-pos_separator so that won't trail separator on full length
            if (!((i-offset+1)%pos_separator) && i<l-pos_separator ) {
                str += Group;
            }
        }
        v[0] = str;
    }

    v[1] = (m[1] && v[1])? Decimal+v[1] : "";
    return (isNegative?'-':'') + v[0] + v[1]; //put back any negation and combine integer and fraction.
};

function prevMonth(){
	var thisMonth = this.getMonth();
	this.setMonth(thisMonth-1);
	if(this.getMonth() != thisMonth-1 && (this.getMonth() != 11 || (thisMonth == 11 && this.getDate() == 1)))
	this.setDate(0);
}
function nextMonth(){
	var thisMonth = this.getMonth();
	this.setMonth(thisMonth+1);
	if(this.getMonth() != thisMonth+1 && this.getMonth() != 0)
	this.setDate(0);
}
 
Date.prototype.nextMonth = nextMonth;
Date.prototype.prevMonth = prevMonth;

/*
 * ngProgressLite - small && slim angular progressbars
 * http://github.com/voronianski/ngprogress-lite
 * Dmitri Voronianski http://pixelhunter.me
 * (c) 2013 MIT License
 */

(function (window, angular, undefined) {
	'use strict';

	angular.module('ngProgressLite', []).provider('ngProgressLite', function () {

		// global configs
		var settings = this.settings = {
			minimum: 0.08,
			speed: 300,
			ease: 'ease',
			trickleRate: 0.02,
			trickleSpeed: 500,
			template: '<div class="ngProgressLite"><div class="ngProgressLiteBar"><div class="ngProgressLiteBarShadow"></div></div></div>'
		};

		this.$get = ['$document', function ($document) {
			var $body = $document.find('body');
			var $progressBarEl, status, cleanForElement;

			var privateMethods = {
				render: function () {
					if (this.isRendered()) {
						return $progressBarEl;
					}

					$body.addClass('ngProgressLite-on');
					$progressBarEl = angular.element(settings.template);
					$body.append($progressBarEl);
					cleanForElement = false;

					return $progressBarEl;
				},

				remove: function () {
					$body.removeClass('ngProgressLite-on');
					$progressBarEl.remove();
					cleanForElement = true;
				},

				isRendered: function () {
					return $progressBarEl && $progressBarEl.children().length > 0 && !cleanForElement;
				},

				trickle: function () {
					return publicMethods.inc(Math.random() * settings.trickleRate);
				},

				clamp: function (num, min, max) {
					if (num < min) { return min; }
					if (num > max) { return max; }
					return num;
				},

				toBarPercents: function (num) {
					return num * 100;
				},

				positioning: function (num, speed, ease) {
					return { 'width': this.toBarPercents(num) + '%', 'transition': 'all ' + speed + 'ms '+ ease };
				}
			};

			var publicMethods = {
				set: function (num) {
					var $progress = privateMethods.render();

					num = privateMethods.clamp(num, settings.minimum, 1);
					status = (num === 1 ? null : num);

					setTimeout(function () {
						$progress.children().eq(0).css(privateMethods.positioning(num, settings.speed, settings.ease));
					}, 100);

					if (num === 1) {
						setTimeout(function () {
							$progress.css({ 'transition': 'all ' + settings.speed + 'ms linear', 'opacity': 0 });
							setTimeout(function () {
								privateMethods.remove();
							}, settings.speed);
						}, settings.speed);
					}

					return publicMethods;
				},

				get: function() {
					return status;
				},

				start: function () {
					if (!status) {
						publicMethods.set(0);
					}

					var worker = function () {
						setTimeout(function () {
							if (!status) { return; }
							privateMethods.trickle();
							worker();
						}, settings.trickleSpeed);
					};

					worker();
					return publicMethods;
				},

				inc: function (amount) {
					var n = status;

					if (!n) {
						return publicMethods.start();
					}

					if (typeof amount !== 'number') {
						amount = (1 - n) * privateMethods.clamp(Math.random() * n, 0.1, 0.95);
					}

					n = privateMethods.clamp(n + amount, 0, 0.994);
					return publicMethods.set(n);
				},

				done: function () {
					if (status) {
						publicMethods.inc(0.3 + 0.5 * Math.random()).set(1);
					}
				}
			};

			return publicMethods;
		}];
	});

})(window, window.angular);

angular.module('app', ['ngProgressLite'])
    .config(['ngProgressLiteProvider', function (ngProgressLiteProvider) {
        ngProgressLiteProvider.settings.speed = 1500;
    }]);
//jmosbech-StickyTableHeaders-0.1.10-0-g8682acb
(function(e,i){"use strict";function t(t,a){var s=this;s.$el=e(t),s.el=t,s.id=o++,s.$el.bind("destroyed",e.proxy(s.teardown,s)),s.$clonedHeader=null,s.$originalHeader=null,s.isSticky=!1,s.hasBeenSticky=!1,s.leftOffset=null,s.topOffset=null,s.init=function(){s.options=e.extend({},n,a),s.$el.each(function(){var i=e(this);i.css("padding",0),s.$scrollableArea=e(s.options.scrollableArea),s.$originalHeader=e("thead:first",this),s.$clonedHeader=s.$originalHeader.clone(),i.trigger("clonedHeader."+l,[s.$clonedHeader]),s.$clonedHeader.addClass("tableFloatingHeader"),s.$clonedHeader.css("display","none"),s.$originalHeader.addClass("tableFloatingHeaderOriginal"),s.$originalHeader.after(s.$clonedHeader),s.$printStyle=e('<style type="text/css" media="print">.tableFloatingHeader{display:none !important;}.tableFloatingHeaderOriginal{position:static !important;}</style>'),e("head").append(s.$printStyle)}),s.updateWidth(),s.toggleHeaders(),s.bind()},s.destroy=function(){s.$el.unbind("destroyed",s.teardown),s.teardown()},s.teardown=function(){s.isSticky&&s.$originalHeader.css("position","static"),e.removeData(s.el,"plugin_"+l),s.unbind(),s.$clonedHeader.remove(),s.$originalHeader.removeClass("tableFloatingHeaderOriginal"),s.$originalHeader.css("visibility","visible"),s.$printStyle.remove(),s.el=null,s.$el=null},s.bind=function(){s.$scrollableArea.on("scroll."+l,s.toggleHeaders),s.isWindowScrolling()||(e(i).on("scroll."+l+s.id,s.setPositionValues),e(i).on("resize."+l+s.id,s.toggleHeaders)),s.$scrollableArea.on("resize."+l,s.toggleHeaders),s.$scrollableArea.on("resize."+l,s.updateWidth)},s.unbind=function(){s.$scrollableArea.off("."+l,s.toggleHeaders),s.isWindowScrolling()||(e(i).off("."+l+s.id,s.setPositionValues),e(i).off("."+l+s.id,s.toggleHeaders)),s.$scrollableArea.off("."+l,s.updateWidth),s.$el.off("."+l),s.$el.find("*").off("."+l)},s.toggleHeaders=function(){s.$el&&s.$el.each(function(){var i,t=e(this),l=s.isWindowScrolling()?isNaN(s.options.fixedOffset)?s.options.fixedOffset.outerHeight():s.options.fixedOffset:s.$scrollableArea.offset().top+(isNaN(s.options.fixedOffset)?0:s.options.fixedOffset),o=t.offset(),n=s.$scrollableArea.scrollTop()+l,a=s.$scrollableArea.scrollLeft(),d=s.isWindowScrolling()?n>o.top:l>o.top,r=(s.isWindowScrolling()?n:0)<o.top+t.height()-s.$clonedHeader.height()-(s.isWindowScrolling()?0:l);d&&r?(i=o.left-a+s.options.leftOffset,s.$originalHeader.css({position:"fixed","margin-top":0,left:i,"z-index":1}),s.isSticky=!0,s.leftOffset=i,s.topOffset=l,s.$clonedHeader.css("display",""),s.setPositionValues(),s.updateWidth()):s.isSticky&&(s.$originalHeader.css("position","static"),s.$clonedHeader.css("display","none"),s.isSticky=!1,s.resetWidth(e("td,th",s.$clonedHeader),e("td,th",s.$originalHeader)))})},s.isWindowScrolling=function(){return s.$scrollableArea[0]===i},s.setPositionValues=function(){var t=e(i).scrollTop(),l=e(i).scrollLeft();!s.isSticky||0>t||t+e(i).height()>e(document).height()||0>l||l+e(i).width()>e(document).width()||s.$originalHeader.css({top:s.topOffset-(s.isWindowScrolling()?0:t),left:s.leftOffset-(s.isWindowScrolling()?0:l)})},s.updateWidth=function(){if(s.isSticky){s.$originalHeaderCells||(s.$originalHeaderCells=e("th,td",s.$originalHeader)),s.$clonedHeaderCells||(s.$clonedHeaderCells=e("th,td",s.$clonedHeader));var i=s.getWidth(s.$clonedHeaderCells);s.setWidth(i,s.$clonedHeaderCells,s.$originalHeaderCells),s.$originalHeader.css("width",s.$clonedHeader.width())}},s.getWidth=function(i){var t=[];return i.each(function(i){var l,o=e(this);if("border-box"===o.css("box-sizing"))l=o.outerWidth();else{var n=e("th",s.$originalHeader);if("collapse"===n.css("border-collapse"))if(document.defaultView&&document.defaultView.getComputedStyle)l=parseFloat(document.defaultView.getComputedStyle(this,null).width);else{var a=parseFloat(o.css("padding-left")),d=parseFloat(o.css("padding-right")),r=parseFloat(o.css("border-width"));l=o.outerWidth()-a-d-r}else l=o.width()}t[i]=l}),t},s.setWidth=function(e,i,t){i.each(function(i){var l=e[i];t.eq(i).css({"min-width":l,"max-width":l})})},s.resetWidth=function(i,t){i.each(function(i){var l=e(this);t.eq(i).css({"min-width":l.css("min-width"),"max-width":l.css("max-width")})})},s.updateOptions=function(i){s.options=e.extend({},n,i),s.updateWidth(),s.toggleHeaders()},s.init()}var l="stickyTableHeaders",o=0,n={fixedOffset:0,leftOffset:0,scrollableArea:i};e.fn[l]=function(i){return this.each(function(){var o=e.data(this,"plugin_"+l);o?"string"==typeof i?o[i].apply(o):o.updateOptions(i):"destroy"!==i&&e.data(this,"plugin_"+l,new t(this,i))})}})(jQuery,window);