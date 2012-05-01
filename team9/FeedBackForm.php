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

        <script> </script>
    </head>
    <body>
        <div>
            <label for="name">please insert feedback for your call:</label>
            <label for="email">Email:</label>
            <input type="email" name="email" required placeholder="email@example.com" />

            <label for="website">ranking for the call</label>
            <input type="text" name="Rank" required placeholder="the ranking" />
            <label for="message">Message:</label>
            <textarea name="message" required></textarea>
            <input type="submit" value="Send Message" />
        </div>
    </body>
</html>
