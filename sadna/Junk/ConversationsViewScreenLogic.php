<?php

include 'ConversationsList.php';

class ManagerConversationsViewScreen {

    public static function showConversationsList() {
        $conList = new ConversationsList();
        $conversations = $conList->GetConversationsList();
        echo '<br>
              <br>';
        echo "<table border='1'>
        <tr>
        <th>conversation_code</th>
        <th>date_of_creation</th>
        <th>client_code</th>
        <th>employee_code</th>
        <th>status</th>        
        </tr>";

        while ($row = mysql_fetch_array($conversations)) {
            echo "<tr>";
            echo "<td>" . $row['conversation_code'] . "</td>";
            echo "<td>" . $row['date_of_creation'] . "</td>";
            echo "<td>" . $row['client_code'] . "</td>";
            echo "<td>" . $row['employee_code'] . "</td>";
            echo "<td>" . $row['status'] . "</td>";
            echo "</tr>";
        }
        echo "</table>";
        $conList->closeDBConnection();
    }

}

?>
