/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function actualizarDlgCr(forma, perNat, perJur)
{
	var chboxEsPerNat = document.getElementById(forma + ":chboxEsPerNat");
	var selPerNat = document.getElementById(forma + ":" + perNat);
	var selPerJur = document.getElementById(forma + ":" + perJur);
	//var boxValue = chbox.value;
	//var outPutValue = cbbox.value;

	if (chboxEsPerNat.checked === true)
	{
		//Usando block, cuando se muestra otra vez, salen 2 celdas en una.
		selPerNat.parentNode.parentNode.style.display = '';
		selPerJur.parentNode.parentNode.style.display = 'none';
	} else
	{
		selPerNat.parentNode.parentNode.style.display = 'none';
		selPerJur.parentNode.parentNode.style.display = '';
	}
}
//document.onload = actualizar('idClientePerNat','idClientePerJur');
/*
 document.addEventListener('DOMContentLoaded', function() {
 //actualizar('idClientePerNat','idClientePerJur');
 var selPerNat = document.getElementById("ProyectosCreateForm:idClientePerNat");
 var selPerJur = document.getElementById("ProyectosCreateForm:idClientePerJur");
 
 selPerNat.parentNode.parentNode.style.display = 'none';
 selPerJur.parentNode.parentNode.style.display = '';
 }, false);
 */
function actualizarDlgEd(forma, perNat, perJur)
{
	var chboxEsPerNat = document.getElementById(forma + ":chboxEsPerNat");
	var selPerNat = document.getElementById(forma + ":" + perNat);
	var selPerJur = document.getElementById(forma + ":" + perJur);
	//var boxValue = chbox.value;
	//var outPutValue = cbbox.value;

	if (chboxEsPerNat.checked === true)
	{
		//Usando block, cuando se muestra otra vez, salen 2 celdas en una.
		selPerNat.parentNode.parentNode.style.display = '';
		selPerJur.parentNode.parentNode.style.display = 'none';
	} else
	{
		selPerNat.parentNode.parentNode.style.display = 'none';
		selPerJur.parentNode.parentNode.style.display = '';
	}
}