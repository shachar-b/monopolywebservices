/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
!(function($){

    var ChatConsole = function(i_element){
        this.init(i_element);
    }

    ChatConsole.prototype = {
        
        constructor : ChatConsole,

        init : function(i_element){
            this.m_element = i_element;
            //all click events on i_element make it active
            $(i_element).on("click", $.proxy(this.setActive, this));
            //refresh chatconsole every 0.5s to check for new messages
            setInterval(this.refresh, 500);
            this.setActive();
        },

        setActive : function(){
            //when active focus on input field
           $(this.m_element).find("textarea").focus();
        },

        sendMessage : function(){
            //send message - post to database
            //call appendMessage
            var message = $(this.m_element).find("textarea").val();
            //get the user id
            $.ajax({
                type: "POST",
                url : "",
                dataType : "JSON",
                data : {userId : "", conversationId : "", message : message},
                success : function(){},
                error : function(){
                    console.log("Error in sendMessage");
                }
            });
        },

        refresh : function(){
            $.ajax({
                type: "POST",
                url : "",
                dataType : "JSON",
                data : {userId : "", conversationId :""},
                success : function(){},
                error : function(){
                    console.log("Error in refresh");
                }
            });
        },

        receiveMessage : function(){
            //trigger event NewMessageReceived if new message received
            //call append message
            $(this.m_element).trigger({
                type : "NewMessageReceived"
            });
        },

        appendMessage : function(i_messageComposer, i_message, i_time){
            $(this.m_element).find(".messages").append(message(i_messageComposer, i_message, i_time));
        }

    }

    function message(i_messageComposer, i_message, i_time){
        return "<div class='message'>" + i_message + "</div>";
    }

    $.fn.chatConsole = function(){
        return this.each(function(){
            var $this = $(this),
            data = $this.data('chatConsole');
            if(!data) $this.data('chatConsole', new ChatConsole(this));
        });
    }

    $.fn.chatConsole.constructor = ChatConsole;

})(window.jQuery);