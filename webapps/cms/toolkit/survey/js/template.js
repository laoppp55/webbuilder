function Expand(frm)
{
    if (frm != null)
    {
    	var len = frm.length;
    	if (len == undefined)
    	{
    	    len = 1;
    	}
    	
    	if (len > 1)
    	{
    	    for (var i=0; i<len; i++)
    	    {
    	    	if (frm[i].style.display == "")
    	    	{
    	    	    frm[i].style.display = "none";
    	    	}
    	    	else
    	    	{
    	    	    frm[i].style.display = "";
    	    	}
    	    }
    	}
    	else if (len == 1)
    	{
    	    if (frm.style.display == "")
    	    {
    	    	frm.style.display = "none";
    	    }
    	    else
    	    {
    	    	frm.style.display = "";
    	    }
    	}
    }
}


function Select_Template()
{
    var len = form1.Item.length;
    var isSelected = false;
    
    if (len == undefined)
    {
    	len = 1;
    }
    
    if (len == 1)
    {
    	if (form1.Item.checked)
    	{
    	    isSelected = true;
    	}
    }
    else if (len > 1)
    {
    	for (var i=0; i<len; i++)
    	{
    	    if (form1.Item[i].checked)
    	    {
    	    	isSelected = true;
    	    	break;
    	    }
    	}
    }	
    
    if (isSelected == false)
    {
        alert("请选择系统模板！");
        return false;
    }
    else
    {
    	return true;
    }
}


function Switch(frm)
{
    var str = frm.src;
    str = str.substring(str.lastIndexOf("/")+1, str.length);
    
    if (str == "menu_corner_minus.gif")
    {
    	frm.src = "../menu-images/menu_corner_plus.gif";
    }
    else if (str == "menu_corner_plus.gif")
    {
    	frm.src = "../menu-images/menu_corner_minus.gif";
    }

    if (str == "menu_tee_minus.gif")
    {
    	frm.src = "../menu-images/menu_tee_plus.gif";
    }
    else if (str == "menu_tee_plus.gif")
    {
    	frm.src = "../menu-images/menu_tee_minus.gif";
    }
}