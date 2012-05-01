<?php

include_once "FeedBack.php";
include_once 'DBAttribute.php';
include_once 'DBPrime.php';
include_once 'DBTableRepresntionOf.php';
include_once 'addendum/annotations.php';

/**
 *
 * @author omer Shenahr
 * @version 1.1
 */
class Database
{

    /**
     * Instance of the database class
     * @static Database $instance
     */
    private static $instance;
    private $connection;
    private $quary_start = "select * from ";
    private $insert_start = "insert into ";
    private $quary_where = " where ";

    const CilentTable = 'Client';
    const ClientFeedbackTable = 'Client_feedback';
    const BODoumentationTable = ' BO_doumentation';
    const ConversationTable = 'Conversation';
    const ManagerTable = 'Manager';
    const MessageTable = 'Message';
    const RepresentativeTable = 'Representative';
    const requestManagerToConversationTable = 'request_Manager_to_conversation';

    /**
     * Constructor
     * @param $dsn The Data Source Name. eg, "mysql:dbname=testdb;host=127.0.0.1"
     * @param $username
     * @param $password
     */
    private function __construct()
    {
        $db_host = 'mysql4.000webhost.com';
        $db_user = 'a2090532_admin';
        $db_pass = 'boristeam9';
        $db_database = 'a2090532_ccsdata';
        $this->connection = mysql_connect($db_host, $db_user, $db_pass) or die('Unable to establish a DB connection');
        mysql_select_db($db_database, $this->connection);
    }

    function __destruct()
    {
        mysql_close($this->connection);
    }

    /**
     * Gets an instance of the Database class
     *
     * @static
     * @return Database An instance of the database singleton class.
     */
    public static function getInstance()
    {
        if (empty(self::$instance))
        {
            self::$instance = new Database();
        }
        return self::$instance;
    }

    /**
     * Runs a query using the current connection to the database.
     *
     * @param string query
     * @param array $args An array of arguments for the sanitization such as array(":name" => "foo")
     * @return array Containing all the remaining rows in the result set.
     */
    public function query($i_query)
    {
        return mysql_query($i_query);
    }

    /**
     *
     * @param string $sqlTable
     * @param IDBSerializableClass $classInstance
     */
    public function insertObjectIntoDB($sqlTable, $classInstance)
    {
        echo 'insertObjectIntoDB : insert statment: ';
        $insertBody = self::makeInsertBodyFromAnnotatedClass($classInstance);
        $myquary = $this->insert_start . $sqlTable . $insertBody;
        //$myquary=     $this->insert_start . $sqlTable . $classInstance->getInstanceAsDBInsetString();;
        echo $myquary;
        echo '<br/>';
        return $this->query($myquary);
    }

    /**
     *
     * @param type $sqlCondtion
     * @param type $sqlTable
     * @param string $class_name
     * @return array
     */
    public function getObjectArrayForClass($sqlCondtion, $sqlTable, $class_name)
    {
        $sqlQuary = $this->quary_start . $sqlTable;
        /**
         *
         */
        $result = NULL;
        if ($this->connection)//connection is initilized
        {
            if (!empty($sqlCondtion)) //conditaion is not null
            {
                $sqlQuary.=$this->quary_where . $sqlCondtion;
            }
            echo "getObjectArrayForClass: sql query is " . $sqlQuary . "</br>";
            $set = $this->query($sqlQuary);
            $i = 0;



            while ($row = mysql_fetch_array($set))
            {
                $instance = self::initAnnotatedObjectFromDBRow($row, $class_name);
                $result[$i] = $instance;
                $i+=1;
            }
        }

        return $result;
    }

    public static function makeInsertBodyFromAnnotatedClass($obj)
    {
        $reflect = new ReflectionClass($obj);
        $methods = $reflect->getMethods();
        $isFirst = true;
        $InsertStringFileds = "(";
        $InsertStringVals = "(";
        foreach ($methods as $value)
        {
            $method = new ReflectionAnnotatedMethod($obj, $value->name);
            if ($method->hasAnnotation('DBAttribute'))
            {
                $attrib = $method->getAnnotation('DBAttribute');
                if ($attrib->type == 'get')
                {
                    if (!$isFirst)
                    {
                        $InsertStringFileds.=",";
                        $InsertStringVals.=",";
                    }
                    $InsertStringFileds.="`" . $attrib->ColName . "`";
                    $InsertStringVals.="'" . $method->invoke($obj) . "'";
                    $isFirst = false;
                }
            }
        }
        return " " . $InsertStringFileds.=") VALUES " . $InsertStringVals . ")";
    }

    public function updateObjectInDB($obj, $tableName)
    {
        $reflect = new ReflectionClass($obj);
        $methods = $reflect->getMethods();
        $isFirst = true;
        $isFirstPrime = true;
        $updateStringFileds = "";
        $updateStringPrimes = "";
        foreach ($methods as $value)
        {
            $method = new ReflectionAnnotatedMethod($obj, $value->name);
            if ($method->hasAnnotation('DBPrime'))
            {
                $attrib = $method->getAnnotation('DBPrime');
                if (!isFirstPrime)
                {
                    $updateStringPrimes.=" and ";
                }
                $updateStringPrimes.="`" . $attrib->value . "`='" . $method->invoke($obj) . "'";
                $isFirstPrime = false;
            }
            else if ($method->hasAnnotation('DBAttribute'))
            {
                $attrib = $method->getAnnotation('DBAttribute');
                if ($attrib->type == 'get')
                {
                    if (!$isFirst)
                    {
                        $updateStringFileds.=",";
                    }
                    $updateStringFileds.="`" . $attrib->ColName . "`='" . $method->invoke($obj) . "'";
                    $isFirst = false;
                }
            }
        }
        $myquary = "UPDATE " . $tableName . " SET " . $updateStringFileds . " where " . $updateStringPrimes;
        echo $myquary."</br>";
        return $this->query($myquary);
    }

    public static function initAnnotatedObjectFromDBRow($row, $class_name)
    {
        require_once($class_name . ".php");
        $reflect = new ReflectionClass($class_name);
        $methods = $reflect->getMethods();
        $instance = $reflect->newInstanceArgs();

        foreach ($methods as $value)
        {
            $method = new ReflectionAnnotatedMethod($class_name, $value->name);
            if ($method->hasAnnotation('DBAttribute'))
            {
                $attrib = $method->getAnnotation('DBAttribute');
                if ($attrib->type == 'set')
                {
                    $colName = $attrib->ColName;
                    ///echo "initAnnotatedObjectFromDBRow: \$row[" . $colName . "]=" . $row[$colName] . " calling " . $method->getName() . "</br>";
                    $method->invoke($instance, $row[$colName]);
                }
            }
        }
        echo "initAnnotatedObjectFromDBRow: read object" . $instance . "</br>";
        return $instance;
    }

    /**
     * public function isUserIDValid($i_userID)
     * Queries the database to see if the user identified by the given
     * userID exists in the system.
     * If so, returns TRUE. Otherwise (or if the query failed) returns FALSE.
     * @param int $i_userID
     * @return boolean - signals if userID is valid
     */
    public function isUserIDValid($i_userID) {
        $userExists = FALSE;
        //Query DB to see that user with $i_userID exists.
        $queryString = sprintf("SELECT count(*) FROM Client where ID = '%d';", $i_userID);
        $returnVal = $this->query($queryString);
        if ($returnVal != FALSE) {
            $queryResult = mysql_fetch_row($returnVal);
            if ($queryResult[0] > 0) { //User with correct userID exists
                $userExists = TRUE;
            } else {
                ; //User doesnt exist
            }
        } else {
            ; //Select query failed!
        }

        return $userExists;
    }

    /**
     * public function isConversationIDValid($i_conversationID)
     * Queries the database to see if the conversation identified by the
     * given conversation_code exists in the system.
     * If so, returns TRUE. Otherwise (or if the query failed) returns FALSE.
     * @param type $i_conversationID
     * @return boolean  - Signals if conversationID is valid.
     */
    public function isConversationIDValid($i_conversationID) {
        $conversationExists = FALSE;
        //Query DB to see that conversation with $i_conversationID exists.
        $queryString = sprintf("SELECT count(*) FROM Conversation where conversation_code = '%d';", $i_conversationID);
        $returnVal = $this->query($queryString);
        if ($returnVal != FALSE) {
            $queryResult = mysql_fetch_row($returnVal);
            if ($queryResult[0] > 0) { //Conversation with correct ID exists
                $conversationExists = TRUE;
            } else {
                ; //Conversation  doesnt exist
            }
        } else {
            ; // Select query failed!
        }

        return $conversationExists;
    }

}
$run=false;
if($run)
{
$db = Database::getInstance();
$feedback = new FeedBack('12', '395', '295', '195');
$db->insertObjectIntoDB('Client_feedback', $feedback);
$res = $db->getObjectArrayForClass("`feedback_code`='395'", Database::ClientFeedbackTable, 'FeedBack');
$feedback = new FeedBack('127', '1335', '1', 'foreverAlone');
$db->updateObjectInDB($feedback, Database::ClientFeedbackTable);
$res = $db->getObjectArrayForClass("", Database::ClientFeedbackTable, 'FeedBack');
echo '<br/>';
echo $res[0];
echo '<br/>';
echo $res[1];
echo '<br/>';
echo $res[2];
echo '<br/>';
//$db->readObject($feedback);
echo '<br/>';
//$db->readObject($res[2])
}
?>