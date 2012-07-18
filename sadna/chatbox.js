!(function($){
    //create a chatbox
    function createChatbox(options){
        var args = arguments;
        return this.each(function(){
            var $this = $(this)
            , data = $this.data("chatbox")
            , option = $.extend(true, {}, $.fn.chatbox.defaults, typeof options == 'object' && options);
            if(!data) $this.data("chatbox", data = new Chatbox(this, option));
            if(typeof options == 'string'){
                data[options] && data[options](args[1]);
            }
        });
    }
    //destroys a chatbox
    function destroyChatbox(){
        return this.each(function(){
            var $this = $(this)
            , data = $this.data("chatbox");
            if(data) {
                data.destroy && data.destroy();
            }
            $this.removeData("chatbox");
        });
    }

    //chatbox constructor
    var Chatbox = function(element, options){
        //ths root element for the chatbox
        this.$element = $(element);
        this.$element.addClass("chatbox");
        this.options = options;
        //interval for updating the chatbox
        this.interval_update = null;
        //jxhr object for send ajax request
        this.jxhr_send = null;
        //jxhr object for update ajax request
        this.jxhr_update = null;
        //child elements of this chatbox
        this.user = this.options.user;
        this.receiver_id = this.options.receiver_id;
        this.init();
    }
    
    //chatbox prototype
    Chatbox.prototype = {
        constructor : Chatbox,
        //init a chatbox object
        init : function(){
            this.build();
            this.bindEvents();
        },
        //build the chatbox - append elements for input, messages and send
        build : function(){
            this.$element.empty();
            this.$element.append(this.options.login_form);
            this.$element.append(this.options.chatbox);
            if(this.user.id === false){
                this.$element.find(".chatbox-login-form").show();
            }
            else {
                this.$element.find(".chat").show();
            }
        },
        login : function(){
            var that = this;
            var postData = "email=" + this.$element.find(".chatbox-login-form .email").val() + "&password=" + this.$element.find(".chatbox-login-form .password").val();
            $.ajax({
                type : "POST",
                url : "chatbox-login.php",
                data : postData,
                dataType : "json",
                success : function(data){
                    if(data.user_exists == false){}
                    else {
                        that.user = data.user;
                        that.$element.find(".chatbox-login-form").fadeOut("fast", function(){
                            that.$element.find(".chat").show();
                            that.$element.find(".user-name").text(data.user.first_name); 
                        }); 
        				
                    }
                },
                error : function(){
                    that.error("login");
                }
            });
        },
        //update the messages area
        update : function(){
            var that = this;
            var postData = "sender_id=&date="
            this.jxhr_update = $.ajax({
                type : "POST",
                url : "",
                success : function(data){
                    that.updateSuccess(data);
                },
                error : function(){
                    that.error("update");
                }
            });
        },
        //called on update error
        updateError : function(){
            this.triggerEvent({
                type : "error",
                data : {
                    action : "update"
                }
            }); 
        },
        //append a message to the messages area
        appendMessage : function(name, type, msg){
            var formattedMsg = $("<div class='msg'><span class='" + type + "'>" + name + "</span> : " + msg + "</div>");
            this.$element.find(".messages").append(formattedMsg);
        },
        //send a messasge
        sendMessage :function(){
            var txtarea = this.$element.find("textarea")
            , msg = txtarea.val();
            txtarea.val("");
            if(msg === '') return;
            this.appendMessage(this.user.first_name,"sender", msg);
            var that = this;
            var postData = "sender_id=&receiver_id=&message=";
            this.jxhr_send = $.ajax({
                type : "POST",
                url : "",
                data : postData,
                success : function(data){
                    that.sendSuccess(data);
                },
                error : function(){
                    that.error("send");
                }
            });
        },
        error : function(act){
            this.triggerEvent({
                type : "error",
                data : {
                    action : act
                } 
            }); 
        },
        //called when message sent successfully
        sendSuccess : function(){
            this.triggerEvent({
                type : "send"
            }); 
        },
        //abort a send operation
        abortSend : function(){
            this.jxhr_send.abort && this.jxhr_send.abort();
        },
        //abort an update operation
        abortUpdate :function(){
            this.jxhr_update.abort && this.jxhr_updateabort();
        },
        //abort all jxhr objects
        abortAll : function(){
            this.abortSend();
            this.abortUpdate();
        },
        //starts the chatbox
        start : function(){
            this.interval_update = setInterval("", 2000);
        },
        //stops a chatbox
        stop : function(){
            clearInterval(this.interval_update);
            this.abortAll();
        },
        //destroys a chatbox object
        destroy : function(){
            this.clear();
            this.stop();
            this.unbindEvents();
            this.$element.empty();
        },
        //clears the screen
        clear :function(){
            this.$element.find(".messages").empty();
        },
        //bind events to chatbox components
        bindEvents : function(){
            var that = this;
            var login_btn = this.$element.find(".login-btn");
            $("body").on("click.chatbox.login", ".login-btn", function(e){
                e.stopPropagation();
                e.preventDefault();
                that.login();
            });
            var send_btn = this.$element.find(".send-btn");
            $("body").on("click.chatbox.send-msg", ".send-btn", function(e){
                e.stopPropagation();
                e.preventDefault();
                that.sendMessage();
            });
        },
        //remove the events
        unbindEvents : function(){

        },
        //trigger a chatbox event
        triggerEvent : function(evt_args){
            evt_args = $.extend(true,{
                type : "",
                data : {}
            }, typeof evt_args == 'object' && evt_args);
            $("body").trigger(evt_args);
        }
    }
    
    $.fn.chatbox = function(options){
        if(typeof options == 'string' && options == 'destroy'){
            destroyChatbox.apply(this);
        }
        else {
            createChatbox.apply(this, arguments);
        }
    }
    
    $.fn.chatbox.defaults = {
        user : {
            id : false
        }
        , 
        receiver_id : false
        , 
        login_form : "<form class='hide well span4 chatbox-login-form' action='' method='POST'>"+
        "<h1>Login</h1>"+
        "<br />"+
        "<label for='email'>Email</label>"+
        "<input type='email' name='email' class='email' />"+
        "<label for='password'>Password</label>"+
        "<input type='password' name='password' class='password' />"+
        "<br />"+
        "<button class='btn login-btn' type='submit'>Login</button>"+
        "<br />"+
        "<br />"+
        "<p class='hide'>Don't have a user?<a href='signup.php'>sign up now!</a></p>"+
        "</form>"
        , 
        chatbox : "<div class='chat hide well span4'><div class='span3'><p>Welcome, <span class='user-name'></span></p><div class='messages'></div><br /><textarea></textarea><button class='btn send-btn'>Send</button><br /></div></div>"
    }
    
})(window.jQuery);


