<?php
    require "ConnectToDB.php";

    $queryGetAllData = "select * from student";
    $data = mysqli_query($connect, $queryGetAllData);

    class Student {
        public function __construct($id, $name, $address, $birthdate) {
            $this->id = (int)$id;
            $this->name = $name;
            $this->address = $address;
            $this->birthDate = $birthdate;
        }
    }

    $arrStudent = array();

    while($row = mysqli_fetch_assoc($data)) {
        array_push($arrStudent, new Student($row['ID'], $row['Name'], $row['Address'], $row['BirthDate']));
    }

    echo json_encode($arrStudent);
?>