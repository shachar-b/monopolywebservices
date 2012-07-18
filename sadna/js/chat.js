/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


!(function($){

    var $chat = {};

    $chat.utils = {
        submitForm : function(args){

            var default_args = {
                url : "",
                form : "",
                fnSuccess : function() {return;},
                fnError : function() {return;}
            }

            args = $.extend({}, default_args, args);
             $.ajax({
                type: 'POST',
                url: args.url,
                data  : $(args.form).serialize(),
                success: function(){
                    args.fnSuccess();
                    console.log("success");
                },
                error : function(){
                    args.fnError();
                }
            });
        }
    }
    
    window.$chat || (window.$chat = $chat);

})(window.jQuery);