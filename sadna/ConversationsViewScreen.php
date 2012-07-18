
<html>
    <head>
        <h1>All Representatives</h1>

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
        <div id="upperContainer" >
            <h1>Conversations</h1>
                <div id ="chat" class="css_left">
                    <h2>Name: John</h2>
                    <h3>Chat</h3>
                    <textarea name ="text" cols="20" rows="5"></textarea>
                    <br> 
                    <br>
                    <textarea name ="text" cols="20" rows="3"></textarea>
                    <input class="btn" type="button" value="Reply">
                </div>
            
                <div id="infoHistoryBox" class="css_right">
                    <div>
                        <ul class="nav nav-tabs">
                            
                            <li class="active"><a href="#">Info</a></li>
                            <li><a href="#">History</a></li>

                        </ul>
<!--                        <h3>Info</h3>

                    here will be information about employee that was chosen from the table

                    </div>
                    <div>
                        <h3>History</h3>

                    here will be conversations history of chosen employee

                    </div>-->
    <!--                <div>#include file="BackOfficeDocumentationForm.html"</div>-->
                </div>                                
        </div>
        
        
    
        <div class="bottom">
            <div id="container" >
                

                <table id="datatables" class="tablesorter table-bordered" >
                    
<!--                                    <p class="bottom">Conversations List</p>-->
                    <thead>
                        <tr>
                            <th>conversation_code</th>
                            <th>date_of_creation</th>
                            <th>client_code</th>
                            <th>employee_code</th>
                            <th>status</th>
                        </tr>
                    </thead>
                
                    <tbody>
                        <?php
                        include_once  'ConversationsList.php';
                        $convObj = new ConversationsList();
                        $convList = $convObj->GetConversationsList();
                        if (count($convList) == 0) {
                            echo 'There is no representatives in database';
                        } else {
                            while($row= mysql_fetch_array($convList)){                               
                        ?>
                        <tr>
                            <td><?=$row['conversation_code']?></td>
                            <td><?=$row['date_of_creation']?></td>
                            <td><?=$row['client_code']?></td>
                            <td><?=$row['employee_code']?></td>
                            <td><?=$row['status']?></td>
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