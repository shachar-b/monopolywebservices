<html>
    <head>
    <h1>All Representatives</h1>
         <title>All Representatives</title>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <script type="text/javascript" src ="js/jquery-1.7.2.min.js"></script>
         <script type="text/javascript" src ="js/metadata.js"></script>
         <script type="text/javascript" src ="js/DataTables/js/jquery.dataTables.min.js"></script>
         <script type="text/javascript" src="js/DataTables/js/jquery.tablesorter.js"></script>
         <script type="text/javascript" src="bootstrap/js/bootstrap-modal.js"></script>
         <link type="text/css" rel="stylesheet" href="bluetable/style.css">
         
          <script type="text/javascript" src="js/metadata.js"></script>
         <style type="text/css">
             @import"js/DataTables/css/demo_table.css";
             @import"css/bootstrap.css";
             @import"js/DataTables/css/demo_page.css";
             @import"js/DataTables/css/demo_table_jui.css";
             @import"js/DataTables/css/jquery.dataTables.css";
             @import"js/DataTables/css/jquery.dataTables_themeroller.css";
         </style>
        <style>
        *{
            font-family: arial;
        }
        </style>
         
         <script type="text/javascript" charset="utf-8">
             $(document).ready(function(){
                 $('#datatables').dataTable({
                    "sPaginationType":"full_numbers",
                    "aaSorting":[[2, "desc"]],
                    "bJQueryUI":true
                 });
             })
         </script>

             <script type="text/javascript">
$(document).ready(function() { 
    $("datatables") 
    .tablesorter({widthFixed: true, widgets: ['zebra']}) 
//    .tablesorterPager({container: $("#pager")}); 
});
    </script>

    </head>
    <body>
           
  <div id="modals" class="paging_two_button ">
                   
            
                <a class="btn css_right" data-toggle="modal" href="#myModal" >Mail To All</a>
                <a class="btn css_right" data-toggle="modal" href="#RegistrationModal" >Register a new employee</a>

                <div class="modal hide" id="myModal">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>Mail to all</h3>
                    </div>

                    <div class="modal-body">
                        <?php

                            include 'MailSenderForm.html';                    
                        ?>
                    </div>

                    <div class="modal-footer">
                        <a href="#" class="btn" data-dismiss="modal">Close</a>
                    </div>
                </div>
            
            
            
            <div class="modal hide" id="RegistrationModal">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>New employee registration</h3>
                </div>

                <div class="modal-body">
                    <?php
                    include 'EmployeeRegistrationForm.php';
                ?>


                </div>

                <div class="modal-footer">
                    <a href="#" class="btn" data-dismiss="modal">Close</a>
                </div>

            </div>
            
        </div>

        <!-- Displaying table of representatives from DB-->
    <div class="center ">
        <div id="container" >
            <table id="datatables" class="tablesorter table-bordered" >
                <thead>
                    <tr>                        
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Phone</th>
                        <th>E-mail</th>
                        <th>UserName</th>
                        <th>Password</th>
                        <th>Permission</th>
<!--                        <th>Info/Editing</th>-->
                    </tr>                    
                </thead> 
                <tbody>
                    <?php
                    include_once 'EmployeeList.php';
                    $employeeObj = new EmployeeList();
                    $repsList=$employeeObj->GetRepresentativeList();
                    if (count($repsList) == 0) {
                        echo 'There is no representatives in database';
                    } else {
                        while($row= mysql_fetch_array($repsList)){                               
                    ?>
                    <tr>
                        <td><?=$row['FirstName']?></td>
                        <td><?=$row['LastName']?></td>
                        <td><?=$row['Phone']?></td>
                        <td><?=$row['E-mail']?></td>
                        <td><?=$row['UserName']?></td>
                        <td><?=$row['Password']?></td>
                        <td><?=$row['Permission']?></td>

                    </tr>
                    <?php
                        }
                    }
                    ?>
                </tbody>
            </table> 
        </div>
    </div>
        
              
</body>

</html>