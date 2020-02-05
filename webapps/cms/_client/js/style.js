// JavaScript Document

function g(o){return document.getElementById(o);}
function Hovertab_m(num,counts,tabname,tabclass,tabcolor){
for (i=1;i<=counts;i++)
{
g(tabname+'tab0'+i).className=tabclass+'tbs';
g(tabname+'div0'+i).style.display='none';
g(tabname+'more0'+i).style.display='None';
}
g(tabname+'tab0'+num).className=tabclass+'tbs_act' ;
g(tabname+'div0'+num).style.display='block';
g(tabname+'more0'+num).style.display='block';
}

function Hovertab(num,counts,tabname,tabclass,tabcolor){
for (i=1;i<=counts;i++)
{
g(tabname+'tab0'+i).className=tabclass+' ';
g(tabname+'div0'+i).style.display='none';
}
g(tabname+'tab0'+num).className=tabclass+'selectL' ;
g(tabname+'div0'+num).style.display='block';
}