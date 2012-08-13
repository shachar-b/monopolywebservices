<?php

include_once 'DBAttribute.php';
include_once 'DBPrime.php';
include_once 'DBTableRepresntionOf.php';
include_once 'addendum/annotations.php';

/**
 *
 * @author omer Shenahr
 * @version 1.1
 */
class DataBase {

    /**
     * Instance of the database class
     * @static Database $instance
     */
    private static $instance;
    private $connection = NULL;
    private $quary_start = "select * from ";
    private $insert_start = "insert into ";
    private $quary_where = " where ";
    private $tableToClassDictionary;

    const CilentTable = 'Client';
    const ClientFeedbackTable = 'Client_feedback';
    const BODoumentationTable = ' BO_doumentation';
    const ConversationTable = 'Conversation';
    const ManagerTable = 'Manager';
    const SelectManagersQuary = '(Select * from employees, users where user_id=id and permission_code=1) as Managers';
    const SelectEmpQuary = '(Select * from employees, users where user_id=id and permission_code=2) as rep';
    const SelectClientQuary = '(Select * from clients, users where user_id=id and permission_code=3) as usr';
    const MessageTable = 'Message';
    const RepresentativeTable = 'Representative';
    const requestManagerToConversationTable = 'request_Manager_to_conversation';

    /**
     * Constructor
     * @param $dsn The Data Source Name. eg, "mysql:dbname=testdb;host=127.0.0.1"
     */
    public function __construct() {
        $db_host = 'mysql4.000webhost.com';
        $db_user = 'a2090532_admin';
        $db_pass = 'boristeam9';
        $db_database = 'a2090532_ccsdata';
        $this->connection = mysql_connect($db_host, $db_user, $db_pass) or die('Unable to establish a DB connection');
        mysql_select_db($db_database, $this->connection);
        $this->tableToClassDictionary = array(
            self::ManagerTable => 'Employee',
            self::RepresentativeTable => 'Employee',
            self::CilentTable => 'User',
            self::ClientFeedbackTable => 'FeedBack',
            self::ConversationTable => 'Conversation',
            self::MessageTable => 'Message'
        );
    }

    public function __destruct() {
        mysql_close($this->connection);
    }

    /**
     * Gets an instance of the Database class
     *
     * @static
     * @return Database An instance of the database singleton class.
     */
    public static function getInstance() {
        if (empty(self::$instance)) {
            self::$instance = new Database();
        }
        return self::$instance;
    }

    public function getConnection() {
        return $this->connection;
    }

    /**
     * Runs a query using the current connection to the database.
     *
     * @param string query
     * @param array $args An array of arguments for the sanitization such as array(":name" => "foo")
     * @return array Containing all the remaining rows in the result set.
     */
    public function query($i_query) {
        $result = mysql_query($i_query) or die("Query failed: " . mysql_error());
        return $result;
    }

    public function getTableForPersonID($i_userID) {
        $dataBase = self::getInstance();
        if ($dataBase->isManngerIDValid($i_userID)) {
            return self::ManagerTable;
        } else if ($dataBase->isRepresentativeIDValid($i_userID)) {
            return self::RepresentativeTable;
        } else if ($dataBase->isUserIDValid($i_userID)) {
            return self::CilentTable;
        }
    }

    public function getClassNameForTable($i_TableName) {
        return $this->tableToClassDictionary[$i_TableName];
    }

    /**
     *
     * @param string $sqlTable
     * @param IDBSerializableClass $classInstance
     */
    public function insertObjectIntoDB($sqlTable, $classInstance) {
        if($sqlTable== DataBase::CilentTable || $sqlTable== DataBase::ManagerTable || $sqlTable== DataBase::RepresentativeTable)
        {
            $sqlTable= 'users';
        }
        //echo 'insertObjectIntoDB : insert statment: ';
        $insertBody = self::makeInsertBodyFromAnnotatedClass($classInstance);
        $myquary = $this->insert_start . $sqlTable . $insertBody;
        //$myquary=     $this->insert_start . $sqlTable . $classInstance->getInstanceAsDBInsetString();;
        //echo $myquary;
        //echo '<br/>';
        return $this->query($myquary);
    }

    /**
     *
     * @param type $sqlCondtion
     * @param type $sqlTable
     * @param string $class_name
     * @return array
     */
    public function getObjectArrayForClass($sqlCondtion, $sqlTable, $class_name) {
        if($sqlTable== DataBase::CilentTable)
        {
            $sqlTable= DataBase::SelectClientQuary;
        }
        else if($sqlTable== DataBase::RepresentativeTable)
        {
            $sqlTable= DataBase::SelectEmpQuary;
        }
        else if($sqlTable == DataBase::ManagerTable)
        {
            $sqlTable=  DataBase::SelectManagersQuary;
        }

        $sqlQuary = $this->quary_start . $sqlTable;
        /**
         *
         */
        $result = NULL;
        if ($this->connection) {//connection is initilized
            if (!empty($sqlCondtion)) { //conditaion is not null
                $sqlQuary.=$this->quary_where . $sqlCondtion;
            }
            // echo "getObjectArrayForClass: sql query is " . $sqlQuary . "</br>";
            $set = $this->query($sqlQuary);
            $i = 0;



            while ($row = mysql_fetch_array($set)) {
                $instance = self::initAnnotatedObjectFromDBRow($row, $class_name);
                $result[$i] = $instance;
                $i+=1;
            }
        }

        return $result;
    }

    /**
     *
     * @param type $sqlCondtion
     * @param type $sqlTable
     * @param string $class_name
     * @return object
     */
    public function getObjectForClass($sqlCondtion, $sqlTable, $class_name) {
        $returnedArray = $this->getObjectArrayForClass($sqlCondtion, $sqlTable, $class_name);
        if ($returnedArray) {
            return $returnedArray[0];
        } else {
            return null;
        }
    }

    public static function makeInsertBodyFromAnnotatedClass($obj) {
        $reflect = new ReflectionClass($obj);
        $methods = $reflect->getMethods();
        $isFirst = true;
        $InsertStringFileds = "(";
        $InsertStringVals = "(";
        foreach ($methods as $value) {
            $method = new ReflectionAnnotatedMethod($obj, $value->name);

            if ($method->hasAnnotation('DBAttribute')) {
                $attrib = $method->getAnnotation('DBAttribute');
                if ($attrib->type == 'get') {
                    if (!$isFirst) {
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

    public function updateObjectInDB($obj, $tableName) {
        if($tableName== DataBase::CilentTable || $tableName== DataBase::ManagerTable || $tableName== DataBase::RepresentativeTable)
        {
            $tableName= 'users';
        }
        $reflect = new ReflectionClass($obj);
        $methods = $reflect->getMethods();
        $isFirst = true;
        $isFirstPrime = true;
        $updateStringFileds = "";
        $updateStringPrimes = "";
        foreach ($methods as $value) {
            $method = new ReflectionAnnotatedMethod($obj, $value->name);
            if ($method->hasAnnotation('DBPrime')) {
                $attrib = $method->getAnnotation('DBPrime');
                if (!isFirstPrime) {
                    $updateStringPrimes.=" and ";
                }
                $updateStringPrimes.="`" . $attrib->value . "`='" . $method->invoke($obj) . "'";
                $isFirstPrime = false;
            } else if ($method->hasAnnotation('DBAttribute')) {
                $attrib = $method->getAnnotation('DBAttribute');
                if ($attrib->type == 'get') {
                    if (!$isFirst) {
                        $updateStringFileds.=",";
                    }
                    $updateStringFileds.="`" . $attrib->ColName . "`='" . $method->invoke($obj) . "'";
                    $isFirst = false;
                }
            }
        }
        $myquary = "UPDATE " . $tableName . " SET " . $updateStringFileds . " where " . $updateStringPrimes;
        // echo $myquary."</br>";
        return $this->query($myquary);
    }

    public static function initAnnotatedObjectFromDBRow($row, $class_name) {
        require_once($class_name . ".php");
        $reflect = new ReflectionClass($class_name);
        $methods = $reflect->getMethods();
        $instance = $reflect->newInstanceArgs();

        foreach ($methods as $value) {
            $method = new ReflectionAnnotatedMethod($class_name, $value->name);
            if ($method->hasAnnotation('DBAttribute')) {
                $attrib = $method->getAnnotation('DBAttribute');
                if ($attrib->type == 'set') {
                    $colName = $attrib->ColName;
                    ///echo "initAnnotatedObjectFromDBRow: \$row[" . $colName . "]=" . $row[$colName] . " calling " . $method->getName() . "</br>";
                    $method->invoke($instance, $row[$colName]);
                }
            }
        }
        //echo "initAnnotatedObjectFromDBRow: read object" . $instance . "</br>";
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
        return $this->isColumnValueExists($i_userID, DataBase::CilentTable, "user_id");
    }

    /**
     * public function isManngerIDValid($i_userID)
     * Queries the database to see if the user identified by the given
     * userID exists in the system.
     * If so, returns TRUE. Otherwise (or if the query failed) returns FALSE.
     * @param int $i_userID
     * @return boolean - signals if userID is valid
     */
    public function isManngerIDValid($i_userID) {
        return $this->isColumnValueExists($i_userID, DataBase::SelectManagersQuary, "user_id");
    }

    /**
     * public function isRepresentativeIDValid($i_userID)
     * Queries the database to see if the user identified by the given
     * userID exists in the system.
     * If so, returns TRUE. Otherwise (or if the query failed) returns FALSE.
     * @param int $i_userID
     * @return boolean - signals if userID is valid
     */
    public function isRepresentativeIDValid($i_userID) {
        return $this->isColumnValueExists($i_userID, DataBase::SelectEmpQuary, "user_id");
    }

    public function isColumnValueExists($i_value, $i_table, $i_column) {
        $valueExists = FALSE;
        //Query DB to see that user with $i_userID exists.
        $queryString = sprintf("SELECT count(*) FROM %s where %s = '%s';", $i_table, $i_column, $i_value);
        $returnVal = $this->query($queryString);
        if ($returnVal != FALSE) {
            $queryResult = mysql_fetch_row($returnVal);
            if ($queryResult[0] > 0) { //column value exists
                $valueExists = TRUE;
            } else {
                ; //User doesnt exist
            }
        } else {
            ; //Select query failed!
        }

        return $valueExists;
    }


    /**
     * this function determins the user type 0 -client (defult) 1-rep 2-manager
     * @param long $i_userID - the user id in qustion
     * @return int the user type
     */
   public function getUserType($i_userID)
    {
       $type=0; //TODO: change this to const CLIENT
       $table=$this->getTableForPersonID($i_userID);
      // echo $table;
       if($table == self::RepresentativeTable)
       {
           $type=1;//TODO: change this to REP conts
       }
       else if($table == self::ManagerTable)
       {
           $type=2;
       }
       return $type;
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
        return $this->isColumnValueExists($i_conversationID, DataBase::ConversationTable, "conversation_code");
    }

    /**
     * public function isBodIdValid($i_BodId)
     * Queries the database to see if the bod identified by the given
     * bod_id exists in the system.
     * If so, returns TRUE. Otherwise (or if the query failed) returns FALSE.
     * @param int $i_BodId
     * @return boolean - signals if $i_BodId is valid
     */
    public function isBodIdValid($i_BodId) {
        return $this->isColumnValueExists($i_BodId, DataBase::BODoumentationTable, "ID");
    }

}

$run = false;
if ($run) {

}
?>