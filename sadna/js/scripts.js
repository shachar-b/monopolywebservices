!(function($){

	$(function(){
		focusFirstInput();
		validateLogin();
		validateSignup();
	});
	
	function focusFirstInput(){
		$("body form:first input:first").focus();
	}
	
	function validateLogin(){
		$("form#login-form").validate({
			rules : {
				"password" : "required",
				"email" : { 
					required : true,
					email : true
				}
			}
		});
	}
	
	function validateSignup(){
		$("form#signup-form").validate({
			rules : {
				"first-name" : "required",
				"last-name" : "required",
				"password" : "required",
				"confirm-password" : {
					required : true,
					equalTo : "#password"
				},
				"email" : { 
					required : true,
					email : true
				}
			},
			messages : {
				"confirm-password" : "password must match"
			}
		});
	}

})(window.jQuery);