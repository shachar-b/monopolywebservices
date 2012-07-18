<?php
include_once 'FeedBack.php';
include_once 'ProcessFeedBack.php';

$returnObject = "";
$isSaveSuccessful = FALSE;

$newFeedbackObject = new FeedBack(
                $_POST["conversationCode"],
                $_POST["grade"],
                $_POST["comments"]);

$processFeedBack = new ProcessFeedBack($newFeedbackObject);

if ($processFeedBack->isValid()){
    $isSaveSuccessful = $processFeedBack->saveData();
}
$returnObject = $processFeedBack->sendResponse($isSaveSuccessful);
echo $returnObject;
?>
