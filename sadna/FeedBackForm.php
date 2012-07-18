<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>

 <?php
    include_once 'DataBase.php';
    class FeedBackScreenInitlizer
    {
        public static function insertConversetions()
        {
            $userId=$_POST["user_id"];
            $ConverstionId=$_POST["conversetion_code"];
        }
    }
 ?>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script type="text/javascript" src="jquery-1.7.2.min.js"></script>

        <script type="text/javascript">
        // wait for the DOM to be loaded
        $(document).ready(function() {
            // bind 'myForm' and provide a simple callback function
            $('#myForm').ajaxForm(function(data) {
				if(data=="success")
                alert("Thank you for your comment!");
            });
        });
    </script>
    </head>
    <body>
        <form id="myForm" action="" method="post">
            <label for="name">please insert feedback for your call:</label>
            </br>
            <label for="website">how would you rank the call?</label>
            <select id="rank">
                        <option value="1" selected="selected">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
            </select>
            </br>
            <label for="message">Do you have anything you would like to tell us please insert it here:</label>
            <textarea name="message" ></textarea>
            <input type="submit" value="Send feedback" />
        </form>
    </body>
</html>
