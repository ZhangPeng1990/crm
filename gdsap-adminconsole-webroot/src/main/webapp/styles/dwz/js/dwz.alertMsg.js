var alertMsg = {
	_boxId: "#alertMsgBox",
	_bgId: "#alertBackground",
	_closeTimer: null,
	
	_boxFrag: '<div id="alertMsgBox" class="alert"><div class="alertContent"><div class="#type#"><div class="alertInner"><h1>#title#</h1><div class="msgContent">#message#</div></div><div class="toolBar"><ul>#butFragment#</ul></div></div></div><div class="alertFooter"><div class="alertFooter_r"><div class="alertFooter_c"></div></div></div></div>',
	_butFrag: '<li><a class="button" rel="#callback#" onclick="alertMsg.close()" href="###"><span>#butMsg#</span></a></li>',

	_types: {error:{type:"error", title:"Error"}, info:{type:"information", title:"Information"}, warn:{type:"warning", title:"Warning"}, correct:{type:"correct", title:"Successful"}, confirm:{type:"confirm", title:"Confirmation"}},
	_butMsg: {ok:"OK", yes:"Yes", no:"No", cancel:"Cancel"},
	
	/**
	 * 
	 * @param {Object} type
	 * @param {Object} msg
	 * @param {Object} buttons [button1, button2]
	 */
	_open: function(type, msg, buttons){
		$(this._boxId).remove();
		var butsHtml = "";
		if (buttons) {
			for (var i = 0; i < buttons.length; i++) {
				var sRel = buttons[i].call ? "callback" : "";
				butsHtml += this._butFrag.replace("#butMsg#", buttons[i].name).replace("#callback#", sRel);
			}
		}
		var boxHtml = this._boxFrag.replace("#type#", type.type).replace("#title#", type.title).replace("#message#", msg).replace("#butFragment#", butsHtml);
		$(boxHtml).appendTo("body").css({top:-$(this._boxId).height()+"px"}).animate({top:"0px"}, 500);
		if (this._closeTimer) {
			clearTimeout(this._closeTimer);
			this._closeTimer = null;
		}
		if (this._types.info == type || this._types.correct == type){
			this._closeTimer = setTimeout(function(){alertMsg.close()}, 3000);
		} else {
			$(this._bgId).show().height($(document).height());
		}
		var jCallButs = $(this._boxId).find("[rel=callback]");
		for (var i = 0; i < buttons.length; i++) {
			jCallButs.eq(i).click(buttons[i].call);
		}
	},
	close: function(){
		$(this._boxId).animate({top:-$(this._boxId).height()}, 180, function(){
			$(this).remove();
		});
		$(this._bgId).hide();
	},
	error: function(msg, options) {
		this._alert(this._types.error, msg, options);
	},
	info: function(msg, options) {
		this._alert(this._types.info, msg, options);
	},
	warn: function(msg, options) {
		this._alert(this._types.warn, msg, options);
	},
	correct: function(msg, options) {
		this._alert(this._types.correct, msg, options);
	},
	_alert: function(type, msg, options) {
		var op = {okName:this._butMsg.ok, okCall:null};
		$.extend(op, options);
		var buttons = [
			{name:op.okName, call: op.okCall}
		];
		this._open(type, msg, buttons);
	},
	/**
	 * 
	 * @param {Object} msg
	 * @param {Object} options {okName, okCal, cancelName, cancelCall}
	 */
	confirm: function(msg, options) {
		var op = {okName:this._butMsg.ok, okCall:null, cancelName:this._butMsg.cancel, cancelCall:null};
		$.extend(op, options);
		var buttons = [
			{name:op.okName, call: op.okCall},
			{name:op.cancelName, call: op.cancelCall}
		];
		this._open(this._types.confirm, msg, buttons);
	}
};

