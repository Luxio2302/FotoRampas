<?php

require_once("conectar.php");
	
	class SignupUser {
		
		private $db;
        private $conexion;
		
		function __construct() {
			$this -> db = new Conectar();
			$this -> conexion = $this->db->conexion();
        }
        
		public function does_user_exist($username,$email,$password,$nombres)
		{
            $query = "Select * from usuarios where email='$email'";
            $result = $this -> conexion->prepare($query);
            $result->execute();
            
			if($result->rowCount() == 1){
              
				$json['error'] = 'Ya existe un usuario con '.$email;
				echo json_encode($json);
			}else{
				//registro
                $query = "insert into usuarios (username, email, password, nombres) values (?,?,?,?)";
				$inserted = $this->conexion->prepare($query);
				
				$inserted->bindParam(1, $username, PDO::PARAM_STR); 
				$inserted->bindParam(2, $email, PDO::PARAM_STR); 
				$inserted->bindParam(3, $password, PDO::PARAM_STR);
				$inserted->bindParam(4, $nombres, PDO::PARAM_STR);
			
				if($inserted->execute()){
					$json['success'] = 'Cuenta creada';

					$query = "SELECT iduser_,username,email,nombres FROM usuarios WHERE email = ?";

					try {
						// Preparar sentencia
						$comando = $this->conexion->prepare($query);
						// Ejecutar sentencia preparada
						$comando->execute(array($email));
						// Capturar primera fila del resultado
						$row = $comando->fetch(PDO::FETCH_ASSOC);
					
						$json['usuario'][]=$row;
					
					} catch (PDOException $e) {
						// Aquí puedes clasificar el error dependiendo de la excepción
						// para presentarlo en la respuesta Json
						return -1;
					}

				}else{
					$json['error'] = 'Se produjo un error';
				}
				echo json_encode($json);
			}    
			
		}
		
	}
	
	$signupUser = new SignupUser();
	if(isset($_POST['username'],$_POST['email'],$_POST['password'],$_POST['nombres'])) {
		$username = $_POST['username'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$nombres = $_POST['nombres'];
		
        
		if(!empty($email) && !empty($password) && !empty($nombres)){
			
			$encrypted_password = md5($password);
			$signupUser-> does_user_exist($username,$email,$password,$nombres);
			
		}else{
			echo json_encode("debe escribir ambas entradas");
		}
		
	}
?>