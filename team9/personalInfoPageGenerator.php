<?php
include_once 'DataBase.php';
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of personalInfoPageGenerator
 *
 * @author omer
 */
class personalInfoPageGenerator
{

    private $m_editorID;
    private $m_queriedUserJSON;
    private $m_queryingUserJSON;
    private $m_isViewingAllowed;
    private $m_isEditAllowed;

    public function __construct($i_queriedUserID, $i_queryingUserID)
    {
        $this->m_editorID = $i_queryingUserID;
        $this->m_queriedUserJSON = self::getUserFromDatabase($i_queriedUserID);
        $this->m_queryingUserJSON = self::getUserFromDatabase($i_queryingUserID);
        $this->m_isViewingAllowed = self::canEditorSeeEditedInfo($i_queryingUserID, $i_queriedUserID);
        $this->m_isEditAllowed = FALSE;
        if ($this->m_isViewingAllowed)
        {
            $this->m_isEditAllowed = self::canEditorEditEdited($i_queryingUserID, $i_queriedUserID);
        }
    }

    public function getInitScripttAccordingToViewingPreviliges()
    {
        $retVal = "
            disableAllInputs();
            ";
        if ($this->m_isViewingAllowed)
        {
            $retVal.= $this->getUserDataListScript($this->m_queriedUserJSON, $this->m_queriedUserJSON);
            if ($this->m_isEditAllowed)
            {
                $retVal.= "
            $('#saveOrEdit').html(\"<button class='btn' id='editBtn'>Edit</button>\");
            $('#editBtn').click(onEdit);
            $('#editBtn').removeAttr('disabled', 'disabled');"; //prevent from being disabled by disable all
            }
        }
        else
        {
            $retVal .= "$('#errorMSG').addClass('alert alert-error');
                       $('#errorMSG').html('<p>you can not see this users data, please contant support</p>');";
        }
        return $retVal;
    }

    function getJSFunctionsAccordingToPremmisions()
    {
        $retVal = "";
        if ($this->m_isViewingAllowed)
        {
            $retVal.="
                function disableAllInputs(){
                $(':input').attr('disabled', 'disabled');
            }";
            if ($this->m_isEditAllowed)
            {
                $retVal.= "
                    function enableAllInputs(){
                $(':input').removeAttr('disabled', 'disabled');
            }
                    function onEdit()
            {
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

            }
            ";
            }
        }
        return $retVal;
    }

    public function getUserDataListIfAllowed()
    {
        $retVal = "";
        if ($this->m_isViewingAllowed)
        {
            $retVal = "<dl id='params' class='dl-horizontal'>
                <dt>Username</dt>
                <dd><input id='userNameLbl' type='text'/></dd>
                <dt>First Name</dt>
                <dd><input id='firstNameLbl' type='text'/></dd>
                <dt>Last Name</dt>
                <dd><input id='lastNameLbl' type='text'/></dd>
                <dt>Phone</dt>
                <dd><input id='phoneLbl' type='text'/></dd>
                <dt>eMail</dt>
                <dd><input id='emailLbl' type='text'/></dd>
                <dt>Password</dt>
                <dd><input id='passwordLbl' type='text'/></dd>
                <dt>Rank of user:</dt>
                <dd><input id='rankLbl' type='text'/></dd>
                <dt>Permission of employee:</dt>
                <dd><input id='permisLbl' type='text'/></dd>
            </dl>";
            if ($this->m_isEditAllowed)
            {
                $retVal.= "<div id='saveOrEdit' class='btn-group'>
                </div>
                ";
            }
        }
        return $retVal;
    }

    public function getUserDataListScript($queriedUserJSON, $queryingUserJSON)
    {
        return "var queriedUserJSON = " . $queriedUserJSON . ";
                var queryingUserJSON = " . $queryingUserJSON . ";
                var queriedUserID = queriedUserJSON['ID'];
                var queryingUserID = queryingUserJSON['ID'];
                $('#firstNameLbl').val(queriedUserJSON['FirstName']);
                $('#lastNameLbl').val(queriedUserJSON['LastName']);
                $('#phoneLbl').val(queriedUserJSON['Phone']);
                $('#emailLbl').val(queriedUserJSON['E-mail']);
                $('#userNameLbl').val(queriedUserJSON['UserName']);
                $('#passwordLbl').val(queriedUserJSON['Password']);
                $('#rankLbl').val(queriedUserJSON['Rank']);
                $('#permisLbl').val(queriedUserJSON['Permission']);";
    }

    public static function getUserFromDatabase($i_UserID)
    {
        // @var Database
        $dataBase = DataBase::getInstance();
        $tableName = $dataBase->getTableForPersonID($i_UserID);
        $className = $dataBase->getClassNameForTable($tableName);
        $returnVal = $dataBase->getObjectForClass("ID = " . $i_UserID, $tableName, $className);
        return $returnVal->getJSON();
    }

    public static function canEditorSeeEditedInfo($i_editorID, $i_editedID)
    {
        $retVal = FALSE;
        if ($i_editedID == $i_editorID)
        {
            $retVal = TRUE;
        }
        else
        {
            $editorType = DataBase::getInstance()->getUserType($i_editorID);
            if ($editorType == 2)
            {
                $retVal = TRUE;
            }
            else
            {
                $editedType = DataBase::getInstance()->getUserType($i_editedID);
                $retVal = $editedType < $editorType;
            }
        }
        return $retVal;
    }

    public static function canEditorEditEdited($i_editorID, $i_editedID)
    {
        $retVal = FALSE;
        $editorType = DataBase::getInstance()->getUserType($i_editorID);
        if ($editorType == 2)
        {
            $retVal = TRUE;
        }
        else
        {
            $editedType = DataBase::getInstance()->getUserType($i_editedID);
            $retVal = $editorType > $editedType;
        }
        return $retVal;
    }

}
?>
