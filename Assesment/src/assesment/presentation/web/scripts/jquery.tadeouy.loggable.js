(function($) {

	$.widget('tadeouy.loggable', {
		options: {
			logLevel: 'ERROR'
		},

		logLevels : {
			'TRACE': 1,
			'DEBUG': 2,
			'INFO': 3,
			'WARN': 4,
			'ERROR': 5,
			'FATAL': 6
		},

		log: function(text, level, verb){
			level = level || 'DEBUG';
			verb = verb || 'log';
			if (this.logLevels[level] >= this.logLevels[this.options.logLevel] && console){
				console[verb](text);
			}
		}
	});

})(jQuery);
