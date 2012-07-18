<?php

include_once 'DataBase.php';
include_once 'Client.php';
include_once 'Employee.php';

/**
 * Description of EmployeeScreenGenerator
 *
 * @author omer
 */
class EmployeeScreenGenerator {

//@var User
    private $m_user;
    private $m_TableForPersonID;
    private $m_isEmployeeManager;

    public function __construct($i_UserID) {
        $this->m_user = self::getUserFromDatabase($i_UserID);
        $this->m_TableForPersonID = DataBase::getInstance()->getTableForPersonID($i_UserID);
        $this->m_isEmployeeManager = $this->m_TableForPersonID == DataBase::ManagerTable;
    }

    public function getFullName() {
        return $this->m_user->getFirstName() . " " . $this->m_user->getLastName();
    }

    public function getUser() {
        return $this->m_user;
    }

    public static function getUserFromDatabase($i_UserID) {
// @var Database
        $dataBase = DataBase::getInstance();
        $tableName = $dataBase->getTableForPersonID($i_UserID);
        $className = $dataBase->getClassNameForTable($tableName);
        $returnVal = $dataBase->getObjectForClass("ID = " . $i_UserID, $tableName, $className);
        return $returnVal;
    }

    public function getScreenTitle() {
        if ($this->m_isEmployeeManager) {
            return "Manager Screen";
        } else {
            return "Representative Screen";
        }
    }

    public function isAllowedToViewPage() {
        $isAllowed = false;
        if ($this->m_TableForPersonID == DataBase::ManagerTable) {
            $isAllowed = true;
        } else if ($this->m_TableForPersonID == DataBase::RepresentativeTable) {
            $isAllowed = true;
        }
        return $isAllowed;
    }

    public function getJSFunctionsAccordingToPremmisions() {
        $retVal = "<script type=\"text/javascript\">
            $(document).ready(init);
                function init() {                                
                initAllEmployeeTabsBehaviour();";
        if ($this->m_isEmployeeManager) {
            $retVal.="
                initManagerTabsBehaviour();";
        }
        $retVal.="}
            
            function initAllEmployeeTabsBehaviour(){
                $('#profileTabBtn').click(function(){
                    data={\"queriedUserID\" :" . $this->m_user->getID() . "};
                    data[\"queryingUserID\"]=" . $this->m_user->getID() . ";
                    $(\"#profileTab\").load(\"PersonalInfo.php\",data);
                });
                                                
                $('#clientConvTabBtn').click(function(){
                    $(\"#clientConvTab\").load('ConversationsViewScreen.php');
                });
            }
            ";
        if ($this->m_isEmployeeManager) {
            $retVal.="
                    function initManagerTabsBehaviour(){
                $('#allClientsTabBtn').click(function(){
                    alert(\"allClientsTab Clicked!\");
                });
                                                
                $('#allRepsTabBtn').click(function(){
                    $(\"#allRepsTab\").load('ManagerRepresentativeViewScreen.php');
                });
                                                
                $('#statisticsTabBtn').click(function(){
                    $(\"#statisticsTab\").load(\"StatisticsScreen.php\");
                });
            }                
                    ";
        }
        $retVal.="</script>";
        return $retVal;
    }

    public function getContentByPermissions() {
        $retVal = "<div class=\"navbar\">
            <div class=\"navbar-inner\">
                <div class=\"container\">
                    <a class=\"brand\">Costumer Communications Service</a>
                    <ul class=\"nav nav-tabs\">
                        <li><a href=\"#welcomeTab\" data-toggle=\"tab\">Welcome Screen</a></li>
                        <li class=\"divider-vertical\"></li>
                        <li><a id=\"profileTabBtn\" href=\"#profileTab\" data-toggle=\"tab\">Profile Page</a></li>
                        <li class=\"divider-vertical\"></li>
                        <li><a id=\"clientConvTabBtn\" href=\"#clientConvTab\" data-toggle=\"tab\">Client Conversations Page</a></li>";
        if ($this->m_isEmployeeManager) {
            $retVal.="<li class=\"divider-vertical\"></li>
                        <li><a id=\"allClientsTabBtn\" href=\"#allClientsTab\" data-toggle=\"tab\">All Clients</a></li>
                        <li class=\"divider-vertical\"></li>
                        <li><a id=\"allRepsTabBtn\" href=\"#allRepsTab\" data-toggle=\"tab\">All Representatives</a></li>
                        <li class=\"divider-vertical\"></li>
                        <li><a id=\"statisticsTabBtn\" href=\"#statisticsTab\" data-toggle=\"tab\">Statistics Screen</a></li>";
        }
        $retVal.="</ul>
                </div>
            </div>
        </div>
        <div class=\"tab-content\" id=\"ManagerPage\">
            <div class=\"tab-pane active\" id=\"welcomeTab\">
                <div class=\"hero-unit\"> 
                    <h1 align=\"center\">
                        Welcome : " . $this->getFullName() . "
                    </h1>
                </div>
            </div>
            <div class=\"tab-pane\" id=\"profileTab\"></div>
            <div class=\"tab-pane\" id=\"clientConvTab\">
                <!-- TODO : Insert page here -->
            </div>";
        if ($this->m_isEmployeeManager) {
            $retVal.="<div class=\"tab-pane\" id=\"allClientsTab\">
                <!-- TODO : Insert page here -->
            </div>
            <div class=\"tab-pane\" id=\"allRepsTab\">
                <!-- TODO : Insert page here -->
            </div>
            <div class=\"tab-pane\" id=\"statisticsTab\">
                <!-- TODO : Insert page here -->
            </div>";
        }
        $retVal.="</div>";

        return $retVal;
    }

}

?>
