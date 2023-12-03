<?php
    require "ConnectToDB.php";

    $id = $_POST['id'];
    $query = "delete from student where ID = '$id'";
    if(mysqli_query($connect, $query)) {
        echo "Delete Success";
    } else {
        echo "Delete Failed";
    }
?>