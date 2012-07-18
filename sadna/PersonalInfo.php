<?php {
    include_once 'PersonalInfoPageGenerator.php';

    $genaretor = new PersonalInfoPageGenerator($_POST['queriedUserID'], $_POST['queryingUserID']);
    $isFullHtm = $_POST['fullHtml'];
    if ($isFullHtm) {
        echo $genaretor->getHeader();
    }
    ?>

    <div id='PersonalInfoForm'>
        <div class='header'><h4>Your profile details: </h4></div>
        <?php echo $genaretor->getUserDataListIfAllowed(); ?>
        <div id="errorMSG">
        </div>
        <div id="sucsessMSG">
        </div>
    </div>
    <?php
    if ($isFullHtm) {
        echo $genaretor->getFooter();
    }
    ?>

    <script type="text/javascript">

    <?php
    echo $genaretor->getInitScripttAccordingToViewingPreviliges();
    echo $genaretor->getJSFunctionsAccordingToPremmisions();
}

if ($isFullHtm) {
    echo "$(document).ready(init);";
} else {
    echo "init()";
}
?>
</script>