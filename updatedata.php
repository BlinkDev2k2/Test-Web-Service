<?php
    require "ConnectToDB.php";

    $id = $_POST['id'];
    $name = $_POST['name'];
    $address =  $_POST['address'];
    $birthDate = $_POST['birthDate'];
    $idd = (int)$id;

    $query = "update student set Name = '$name', Address = '$address', BirthDate = '$birthDate' where ID = $idd";

    if(mysqli_query($connect, $query)) {
        echo "Update Success";
    } else {
        echo "Update Failed";
    }
?>