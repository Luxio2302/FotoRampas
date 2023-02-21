<?PHP
$hostname_localhost ="localhost";
$database_localhost ="control_rampas";
$username_localhost ="root";
$password_localhost ="";

$json=array();
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		$consulta="SELECT * FROM plan_embarque,tb_image WHERE liberado='' AND accion='Carga' AND id_emb = id_plan GROUP BY id_emb ORDER BY id_emb ASC ";
		
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['Usuarios'][]=$registro;
			//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
		}
		mysqli_close($conexion);
		echo json_encode($json);
?>

