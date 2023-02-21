<?php

require_once("conectar.php");
	
	class LoginUser {
		
		private $db;
		private $conexion;
		
		function __construct() {
			$this -> db = new Conectar();
			$this -> conexion = $this->db->conexion();
		}
		
		public function does_user_exist($username,$password)
		{
			$query = "Select * from usuarios where username='$username' and password = '$password'";
			$result = $this -> conexion->prepare($query);
			$result->execute();
			
			if($result->rowCount() == 1){
				$json['success'] = ' Bienvenido '.$username;
				// Al igual como en register_movil, con este json enviamos los datos al MainActivity
				$query = "SELECT iduser_,username,email,nombres FROM usuarios WHERE username = ?";
				
				try {
					// Preparar sentencia
					$comando = $this->conexion->prepare($query);
					// Ejecutar sentencia preparada
					$comando->execute(array($username));
					// Capturar primera fila del resultado
					$row = $comando->fetch(PDO::FETCH_ASSOC);
					$json['usuario'][]=$row;
									
				} catch (PDOException $e) {
					$json['error'] = 'exception';
					// Aquí puedes clasificar el error dependiendo de la excepción
					// para presentarlo en la respuesta Json
					return -1;
				}

				echo json_encode($json);
			}else{
				$json['error'] = 'Las credenciales de inicio de sesión son incorrectas';
				echo json_encode($json);
			}
			
		}
		
	}
	
	$loginUser = new LoginUser();
	if(isset($_POST['username'],$_POST['password'])) {
		$username = $_POST['username'];
		$password = $_POST['password'];
		
		if(!empty($username) && !empty($password)){
			
			$encrypted_password = md5($password);
			$loginUser-> does_user_exist($username,$password);
			
		}else{
			echo json_encode("debe escribir ambas entradas");
		}
		
	}
?>