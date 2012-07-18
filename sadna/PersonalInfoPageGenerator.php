<?php

include_once 'DataBase.php';
include_once 'Client.php';
include_once 'Employee.php';

/**
 * Description of personalInfoPageGenerator
 *
 * @author omer
 */
class PersonalInfoPageGenerator {

    private $m_editorID;
    private $m_queriedUserJSON;
    private $m_queryingUserJSON;
    private $m_isViewingAllowed;
    private $m_isEditAllowed;

    public function __construct($i_queriedUserID, $i_queryingUserID) {
        $this->m_editorID = $i_queryingUserID;
        $this->m_queriedUserJSON = self::getUserFromDatabase($i_queriedUserID);
        $this->m_queryingUserJSON = self::getUserFromDatabase($i_queryingUserID);
        $this->m_isViewingAllowed = self::canEditorSeeEditedInfo($i_queryingUserID, $i_queriedUserID);
        $this->m_isEditAllowed = FALSE;
        if ($this->m_isViewingAllowed) {
            $this->m_isEditAllowed = self::canEditorEditEdited($i_queryingUserID, $i_queriedUserID);
        }
    }

    public function getHeader() {
        return '
            <!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src ="bootstrap/js/bootstrap.min.js"></script>
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <TITLE>Personal Info Screen</TITLE>

    </head>
    <body>
            ';
    }

    public function getFooter() {
        return "    </body>
</html>";
    }

    public function getInitScripttAccordingToViewingPreviliges() {

        $retVal = "";
        if ($this->m_isViewingAllowed) {
            $retVal.=$this->getJsonParams($this->m_queriedUserJSON, $this->m_queriedUserJSON);
        }
        $retVal .= "function init()
            {
            ";
        if ($this->m_isViewingAllowed) {
            $retVal.= "disableAllInputs();
                " . $this->getUserDataListScript();
            if ($this->m_isEditAllowed) {
                $retVal.= "
            $('#saveOrEdit').html(\"<button class='btn' id='editBtn'>Edit</button>\");
            $('#editBtn').click(onEdit);
            $('#editBtn').removeAttr('disabled', 'disabled');"; //prevent from being disabled by disable all
            }
        } else {
            $retVal .= "$('#errorMSG').addClass('alert alert-error');
                       $('#errorMSG').html('<p>You are not permitted to view this user's data, please contact support.</p>');";
        }
        return $retVal . "};";
    }

    function getJSFunctionsAccordingToPremmisions() {
        $retVal = "";
        if ($this->m_isViewingAllowed) {
            $retVal.="
                function disableAllInputs(){
                $(':input').attr('disabled', 'disabled');
            }";
            if ($this->m_isEditAllowed) {
                $retVal.= "
                    function enableAllInputs(){
                $(':input').removeAttr('disabled', 'disabled');
            }
                    function onEdit()
            {
                removeWarnings();
                enableAllInputs();
                $('#saveOrEdit').html(\"<button class='btn' id='saveBtn'>Save</button> \\n\\
        <button class='btn' id='cancelBtn'>Cancel</button>\");
        $('#saveBtn').click(onSave);
        $('#cancelBtn').click(onCancel);

            }
            function onCancel()
            {
                $('#saveOrEdit').html(\"<button class='btn' id='saveBtn'>Save</button> \\n\\
                <button class='btn' id='cancelBtn'>Cancel</button>\");

                init();
            }
            function onSave()
            {
                var queriedUserID = queriedUserJSON['ID'];
        data={\"ID\" : queriedUserID};
        data['callSubmit']=true;
        allInputs = $(\":input\");
        removeWarnings();
        $.each(allInputs, function()
        {


            if(this.type=='text')
            {
                data[this.id]=this.value;
            }

        });
        $.ajax({
            type: 'POST',
            url: 'personalInfoPageGenerator.php',
            data: data,
            success: onSubmitSuccess,
            error: onSubmitError
        });

            }


    function onSubmitSuccess(data)
    {
        if(data)
        {
            queriedUserJSON = $.parseJSON( data );
            init();
            $('#sucsessMSG').addClass('alert alert-success ');
            $('#sucsessMSG').html(\"data have been commited succsussfuly\");
        }
        else
        {
            $('#errorMSG').addClass('alert alert-error');
            $('#errorMSG').html('request not succsessful - please try again- if problem presist contact support.');
        }


    }

    function onSubmitError(data)
    {
        $('#errorMSG').addClass('alert alert-error');
        $('#errorMSG').html('request not succsessful - please try again- if problem presist contact support.');
    }
    function removeWarnings()
    {
        $('#sucsessMSG').removeClass();
        $('#sucsessMSG').html('');
        $('#errorMSG').removeClass();
        $('#errorMSG').html('');
    }

            ";
            }
        }
        return $retVal;
    }

    public function getUserDataListIfAllowed() {
        $retVal = "";
        if ($this->m_isViewingAllowed) {
            $retVal = "<dl id='params' class='dl-horizontal'>

            </dl>";
            if ($this->m_isEditAllowed) {
                $retVal.= "<div id='saveOrEdit' class='btn-group'>
                </div>
                ";
            }
        }
        return $retVal;
    }

    public function getJsonParams($queriedUserJSON, $queryingUserJSON) {
        return "var queriedUserJSON = " . $queriedUserJSON . ";
                var queryingUserJSON = " . $queryingUserJSON . ";";
    }

    public function getUserDataListScript() {
        return "var queriedUserID = queriedUserJSON['ID'];
                var queryingUserID = queryingUserJSON['ID'];
                $('#params').html('');
                $.each(queriedUserJSON, function(text,val)
                {
                             if(text!='ID')
                             {
                                var x=\"<dt>\"+text+ \"</dt> <dd><input id='\"+text+\"' type='text' value='\"+val+\"' disabled/></dd>\";
                                 $('#params').append(x);
                            }
                }
               );";
    }

    public static function getUserFromDatabase($i_UserID) {
        // @var Database
        $dataBase = DataBase::getInstance();
        $tableName = $dataBase->getTableForPersonID($i_UserID);
        $className = $dataBase->getClassNameForTable($tableName);
        $returnVal = $dataBase->getObjectForClass("ID = " . $i_UserID, $tableName, $className);
        return $returnVal->getJSON();
    }

    public static function canEditorSeeEditedInfo($i_editorID, $i_editedID) {
        $retVal = FALSE;
        if ($i_editedID == $i_editorID) {
            $retVal = TRUE;
        } else {
            $editorType = DataBase::getInstance()->getUserType($i_editorID);
            if ($editorType == 2) {
                $retVal = TRUE;
            } else {
                $editedType = DataBase::getInstance()->getUserType($i_editedID);
                $retVal = $editedType < $editorType;
            }
        }
        return $retVal;
    }

    public static function canEditorEditEdited($i_editorID, $i_editedID) {
        $retVal = FALSE;
        $editorType = DataBase::getInstance()->getUserType($i_editorID);
        if ($editorType == 2) {
            $retVal = TRUE;
        } else {
            $editedType = DataBase::getInstance()->getUserType($i_editedID);
            $retVal = $editorType > $editedType;
        }
        if ($editedType == 1) {
            $retVal = $retVal || ($editorType == $editedType);
        }
        return $retVal;
    }

    public static function submit() {

        $ok = FALSE;
        $db = DataBase::getInstance();
        $i_FirstName = $_POST['FirstName'];
        $i_SecondName = $_POST['LastName'];
        $i_Phone = $_POST['Phone'];
        $i_Email = $_POST['E-mail'];
        $i_UserName = $_POST['UserName'];
        $i_Password = $_POST['Password'];
        $i_ID = $_POST['ID'];
        $userType = $db->getUserType($i_ID);

        if ($userType == 0) {//write client
            $i_Rank = $_POST['Rank'];
            $client = new Client($i_FirstName, $i_SecondName, $i_Phone, $i_Email, $i_UserName, $i_Password, $i_Rank, $i_ID);
            $ok = $db->updateObjectInDB($client, DataBase::CilentTable);
        } else if ($userType == 1 || $userType == 2) {//write employee
            $i_premmsion = $_POST['Permission'];
            $employee = new Employee($i_FirstName, $i_SecondName, $i_Phone, $i_Email, $i_premmsion, $i_UserName, $i_Password);
            $employee->setID($i_ID);
            if ($userType == 1) {
                $ok = $db->updateObjectInDB($employee, DataBase::RepresentativeTable);
            } else {
                $ok = $db->updateObjectInDB($employee, DataBase::ManagerTable);
            }
        }
        if ($ok) {
            return self::getUserFromDatabase($i_ID);
        }
    }

}

if ($_POST['callSubmit']) {
    echo personalInfoPageGenerator::submit();
}
?>
