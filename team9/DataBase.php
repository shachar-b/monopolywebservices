<?php

include_once "FeedBack.php";

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
    private $quary_where = "where ";
    const CilentTable = 'Client';
    const ClientFeedbackTable = 'Client_feedback';
    const  BODoumentationTable = ' BO_doumentation';
    const ConversationTable = 'Conversation';
    const ManagerTable = 'Manager';
    const MessageTable = 'Message';
    const RepresentativeTable='Representative';
    const requestManagerToConversationTable='request_Manager_to_conversation';
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
        echo 'insert statment: ';
        $myquary = $this->insert_start . $sqlTable . $classInstance->getInstanceAsDBInsetString();
        echo $myquary;
        echo '<br/>';
        echo $this->query($myquary);
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
            if (!$sqlCondtion) //conditaion is not null
            {
                $sqlQuary.=$this->quary_where . $sqlCondtion;
            }
            $set = $this->query($sqlQuary);
            $i = 0;



            while ($row = mysql_fetch_array($set))
            {
                //require_once '\FeedBack.php';
                //$reflector= new ReflectionClass($class_name);
                //$instance=$reflector->newInstance();
                require_once($class_name . ".php");
                //$instance = new $class_name;
                $instance=call_user_func($class_name ."::initInstanceFromDBRows",$row);
                //call_user_method_array("initInstanceFromDBRow", $instance, $row);
                $result[$i] = $instance;
                $i+=1;
            }
        }

        return $result;
    }

}
$db = Database::getInstance();
$feedback = new FeedBack('333', '444', '555', '666');
$db->insertObjectIntoDB('Client_feedback', $feedback);
$res = $db->getObjectArrayForClass("true", Datab, 'FeedBack');
echo $res[0];
echo '<br/>';
echo $res[1];
echo '<br/>';
echo $res[2];
?>