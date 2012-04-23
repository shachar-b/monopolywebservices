<?php

include "FeedBack.php";

/**
 * Database access class.
 * Used in applications where one point of database access is required
 *
 * Typical Usage:
 * $db = Database::getInstance();
 * $results = $db->query("SELECT * FROM test WHERE name = :name",array(":name" => "matthew"));
 * print_r($results);
 *
 * @author Matthew Elliston <matt@e-titans.com>
 * @version 1.0
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
    private $Client_feedback_table_name = 'Client_feedback ';

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
     * @param IDBSerlizebleClass $classInstance
     */
    public function insertObjectIntoDB($sqlTable, $classInstance)
    {
        $quary = $this->insert_start . $sqlTable . $classInstance->getInstanceAsDBInsetString();
        $this->query($quary);
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
$feedback = new FeedBack();
$feedback->init('33', '44', '55', '66');
$db->insertObjectIntoDB('Client_feedback', $feedback);
$res = $db->getObjectArrayForClass("true", 'Client_feedback', 'FeedBack');
echo $res[0];
echo '<br/>';
echo $res[1];
?>