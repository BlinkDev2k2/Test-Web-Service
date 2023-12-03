<?php
    require "ConnectToDB.php";
    $name = $_POST['name'];
    $address = $_POST['address'];
    $birthDate = $_POST['birthDate'];

    $insertData = "insert into student values(null, '$name', '$address', '$birthDate')";

    if(mysqli_query($connect, $insertData)) {
        echo "Success";
    } else {
        echo "Add To Database Error";
    }
?>