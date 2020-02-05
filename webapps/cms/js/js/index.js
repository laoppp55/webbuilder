function setTab03Syn ( i )
	{
		selectTab03Syn(i);
	}
	
	function selectTab03Syn ( i )
	{
		switch(i){
			case 1:
			document.getElementById("TabTab03Con1").style.display="block";
			document.getElementById("TabTab03Con2").style.display="none";
			document.getElementById("font1").style.color="#ffffff";
			document.getElementById("font2").style.color="#000000";
			document.getElementById("font1").style.fontWeight="bold";
			document.getElementById("font2").style.fontWeight="normal";
			break;
			case 2:
			document.getElementById("TabTab03Con1").style.display="none";
			document.getElementById("TabTab03Con2").style.display="block";
			document.getElementById("font1").style.color="#000000";
			document.getElementById("font2").style.color="#ffffff";
			document.getElementById("font1").style.fontWeight="normal";
			document.getElementById("font2").style.fontWeight="bold";
			break;
		}
	}
	
	

function setTab03Syn1 ( i )
	{
		selectTab03Syn1(i);
	}
	
	function selectTab03Syn1 ( i )
	{
		switch(i){
			case 1:
			document.getElementById("TabTab03Con11").style.display="block";
			document.getElementById("TabTab03Con21").style.display="none";
			document.getElementById("font11").style.color="#ffffff";
			document.getElementById("font21").style.color="#000000";
			document.getElementById("font11").style.fontWeight="bold";
			document.getElementById("font21").style.fontWeight="normal";
			break;
			case 2:
			document.getElementById("TabTab03Con11").style.display="none";
			document.getElementById("TabTab03Con21").style.display="block";
			document.getElementById("font11").style.color="#000000";
			document.getElementById("font21").style.color="#ffffff";
			document.getElementById("font11").style.fontWeight="normal";
			document.getElementById("font21").style.fontWeight="bold";
			break;
		}
	}