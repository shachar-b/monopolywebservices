<!DOCTYPE html>
<?php include_once 'EmployeeScreenGenerator.php';
{$genaretor = new EmployeeScreenGenerator($_POST['ID']);
?>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><?php $genaretor->getScreenTitle() ?></title>
        <script type="text/javascript" src="../jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src ="../bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src ="../bootstrap/js/bootstrap-tab.js"></script>
        <link href="../bootstrap/css/bootstrap.css" rel="stylesheet">

        <script type="text/javascript">
            $(document).ready(function() {
               $('#tab2BTN').click(function(){
                    $('<form />')
                    .hide()
                    .attr({ method : "post" })
                    .attr({ action : "PersonalInfo.php"})
                    .attr({target: "personalInfo"})
                    .append($('<input />')
                    .attr("type","hidden")
                    .attr({ "name" : "queriedUserID" })
                    .val(<?php echo $genaretor->getUser()->getID() ?>)
                )
                    .append($('<input />')
                    .attr("type","hidden")
                    .attr({ "name" : "queryingUserID" })
                    .val(<?php echo $genaretor->getUser()->getID() ?>)
                )
                    .append('<input type="submit" />')
                    .appendTo($("body"))
                    .submit();
                    $.height($("#tab2").height());
                    $("#PIframe").width($("#tab2").height());


                });
            });
        </script>
    </head>
    <body>
        <div class="tabbable">
            <ul class="nav nav-tabs">
                <li  class="active"><a href="#tab1" data-toggle="tab">welcome</a></li>
                <li><a id="tab2BTN" href="#tab2" data-toggle="tab">Personal Info Testing</a></li>
                <li><a href="#tab3" data-toggle="tab">Further unknown tests...</a></li>
            </ul>
            <div class="tab-content" id="testingPage">
                <div class="tab-pane active" id="welcome">
                    <h1>
                       welcome <?php echo $genaretor->getFullName();?>
                    </h1>
                </div>
                <div class="tab-pane" id="tab2">

                </div>
                <div class="tab-pane" id="tab3">
                    <p>Enter new tests...</p>
                </div>
            </div>
        </div>
    </body>
</html>
<?php
}
?>