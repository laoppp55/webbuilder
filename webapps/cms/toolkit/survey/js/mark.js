function createStyle(type,columnID)
{
  if (type == 1 || type == 3 || type == 4)
    var retstr = showModalDialog("../template/editStyle.jsp?column="+columnID+"&type="+type,"createStyle","font-family:Verdana;font-size:12;dialogWidth:650px;dialogHeight:440px;status:no");
  else if (type == 6)
    var retstr = showModalDialog("../template/editColumnStyle.jsp?type="+type,"createStyle","font-family:Verdana;font-size:12;dialogWidth:500px;dialogHeight:420px;status:no");
  else
    var retstr = showModalDialog("../template/editOtherStyle.jsp?type="+type,"createStyle","font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");

  if (retstr != "" && retstr != undefined)
  {
    var newoption = document.createElement("OPTION");
    newoption.value = retstr.substring(0, retstr.indexOf(","));
    newoption.text = retstr.substring(retstr.indexOf(",") + 1);

    //article list  //relate article list  //column list  //path  //topstories
    if (type == 1 || type == 3 || type == 4 || type == 6 || type == 8 || type == 9)
    {
      document.all("listType").add(newoption);
      document.all("listType").options[document.all("listType").length-1].selected = true;
    }
    if (type == 2)    //navbar
    {
      document.all("navbar").add(newoption);
      document.all("navbar").options[document.all("navbar").length-1].selected = true;
    }
    if (type == 5)    //readed title
    {
      document.all("sellink").add(newoption);
      document.all("sellink").options[document.all("sellink").length-1].selected = true;
    }
    if (type == 7)    //new article
    {
      document.all("seldays").add(newoption);
      document.all("seldays").options[document.all("seldays").length-1].selected = true;
    }
    if (type == 10)   //next article
    {
      document.all("nextarticle").add(newoption);
      document.all("nextarticle").options[document.all("nextarticle").length-1].selected = true;
    }
  }
}

function updateStyle(type,styleID,columnID)
{
  if (styleID > 0)
  {
    if (type == 1 || type == 3 || type == 4)
      var retstr = showModalDialog("../template/editStyle.jsp?column="+columnID+"&ID="+styleID+"&type="+type,"updateStyle","font-family:Verdana;font-size:12;dialogWidth:650px;dialogHeight:440px;status:no");
    else if (type == 6)
      var retstr = showModalDialog("../template/editColumnStyle.jsp?ID="+styleID+"&type="+type,"updateStyle","font-family:Verdana;font-size:12;dialogWidth:500px;dialogHeight:420px;status:no");
    else
      var retstr = showModalDialog("../template/editOtherStyle.jsp?type="+type+"&ID="+styleID,"updateStyle","font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
  }
}

function previewStyle(type, styleID)
{
  if (styleID > 0)
    showModalDialog("../template/getviewfile.jsp?id="+styleID+"&type="+type,"","font-family:Verdana;font-size:12;dialogWidth:40em;dialogHeight:16em;status:no");
}

function selectStyle(styleID)
{
  if (styleID > 0)
    document.all("title").value = "[TITLESTYLE]" + styleID + "[/TITLESTYLE]";
}

function selectArticleList()
{
  if (markForm.selectArtList[0].checked)
  {
    markForm.artNum.disabled = 0;
    markForm.articleNumInPage.disabled = 1;
    markForm.navbar.disabled = 1;
    markForm.navPosition[0].disabled = 1;
    markForm.navPosition[1].disabled = 1;
    markForm.button1.disabled = 0;
    markForm.button2.disabled = 0;
    markForm.button3.disabled = 0;
    markForm.button4.disabled = 1;
    markForm.button5.disabled = 1;
    markForm.button6.disabled = 1;
    markForm.sellink.disabled = 0;
    markForm.innerFlag[0].disabled = 0;
    markForm.innerFlag[1].disabled = 0;
  }
  if (markForm.selectArtList[1].checked)
  {
    markForm.artNum.disabled = 1;
    markForm.articleNumInPage.disabled = 0;
    markForm.navPosition[0].disabled = 0;
    markForm.navPosition[1].disabled = 0;
    markForm.button1.disabled = 1;
    markForm.button2.disabled = 1;
    markForm.button3.disabled = 1;
    markForm.sellink.disabled = 1;
    markForm.innerFlag[0].checked = 1;
    markForm.innerFlag[0].disabled = 1;
    markForm.innerFlag[1].disabled = 1;
    markForm.navbar.disabled = 0;
    markForm.button4.disabled = 0;
    markForm.button5.disabled = 0;
    markForm.button6.disabled = 0;
    markForm.navPosition[0].checked = 1;
  }
}

function selectNavStyle()
{
  if (markForm.navPosition[0].checked)
  {
    markForm.navbar.disabled = 0;
    markForm.button4.disabled = 0;
    markForm.button5.disabled = 0;
    markForm.button6.disabled = 0;
  }
  else
  {
    markForm.navbar.disabled = 1;
    markForm.button4.disabled = 1;
    markForm.button5.disabled = 1;
    markForm.button6.disabled = 1;
  }
}

function selectLink()
{
  if (markForm.linkradio[0].checked)
  {
    markForm.urlname.disabled = true;
    markForm.param.disabled = true;
    markForm.aid.disabled = true;
    markForm.cid.disabled = true;
  }
  else
  {
    markForm.urlname.disabled = false;
    markForm.param.disabled = false;
    markForm.aid.disabled = false;
    markForm.cid.disabled = false;
  }
}

function displayRange()
{
  if (range.style.display == "")
    range.style.display = "none";
  else
    range.style.display = "";
}

function displaySize()
{
  if (moresize.style.display == "")
    moresize.style.display = "none";
  else
    moresize.style.display = "";
}

function defineAttr()
{
  var num = 0;
  var text = "";
  for (var i=0; i < markForm.selectedColumn.length; i++)
  {
    if (markForm.selectedColumn.options[i].selected)
    {
      text = markForm.selectedColumn.options[i].text;
      break;
    }
  }

  if (text != "")
  {
    var retVal = showModalDialog("alItemAttr.jsp?item="+text,"","font-family:Verdana;font-size:12;dialogWidth:26em;dialogHeight:10em;status:no");
    if (retVal != undefined && retVal != "")
    {
      markForm.selectedColumn.options[i].text = retVal;
    }
  }
}

function delItem()
{
  var select = false;
  for (var i=0; i < markForm.selectedColumn.length; i++)
  {
     if (markForm.selectedColumn[i].selected)
     {
        select = true;
        break;
     }
  }

  if (select)
  {
    for (var i=0; i < markForm.selectedColumn.length; i++)
      if (markForm.selectedColumn[i].selected)
        markForm.selectedColumn[i] = null;
  }
  else
  {
     alert("请选择栏目！");
  }
}

function delArticleItem()
{
  var select = false;
  for (var i=0; i < markForm.selectedArticle.length; i++)
  {
     if (markForm.selectedArticle[i].selected)
     {
        select = true;
        break;
     }
  }

  if (select)
  {
    for (var i=0; i < markForm.selectedArticle.length; i++)
      if (markForm.selectedArticle[i].selected) markForm.selectedArticle[i] = null;
  }
}

function check(type,extendStr,saveas)
{
  if ((markForm.selectArtList[0].checked && markForm.artNum.value == "") || (markForm.selectArtList[1].checked && markForm.articleNumInPage.value == ""))
  {
    alert("请选择要显示的文章数目！");
    return;
  }
  if (!checkNum(markForm.artNum.value) || !checkNum(markForm.articleNumInPage.value))
  {
    alert("文章数目应为整数！");
    return;
  }
  if (markForm.selectArtList[1].checked && markForm.navPosition[0].checked && (markForm.navbar.length == 0 || markForm.navbar.value == 0))
  {
    alert("请选取导航条样式！");
    return;
  }
  var t1 = markForm.time1.value;
  var t2 = markForm.time2.value;
  if (t1 != "" && t2 != "")
  {
    var d1 = new Date();
    d1.setYear(t1.substring(0, 4));
    d1.setMonth(parseInt(t1.substring(5,7)) - 1);
    d1.setDate(t1.substring(8, 10));

    var d2 = new Date();
    d2.setYear(t2.substring(0, 4));
    d2.setMonth(parseInt(t2.substring(5,7)) - 1);
    d2.setDate(t2.substring(8, 10));

    if (d1.getTime() > d2.getTime())
    {
      alert("开始时间应比结束时间小！");
      return;
    }
  }
  if (markForm.num1.value != "" || markForm.num2.value != "")
  {
    if ((markForm.num1.value != "" && !checkNum(markForm.num1.value)) || (markForm.num2.value != "" && !checkNum(markForm.num2.value)))
    {
      alert("序号应为整数！");
      return;
    }
    if (markForm.num1.value != "" && markForm.num2.value != "" && parseInt(markForm.num1.value) > parseInt(markForm.num2.value))
    {
      alert("开始序号应比结束序号小！");
      return;
    }
  }
  if (markForm.titlesize.value == "" || !checkNum(markForm.titlesize.value) ||
      markForm.vicetitlesize.value == "" || !checkNum(markForm.vicetitlesize.value) ||
      markForm.summarysize.value == "" || !checkNum(markForm.summarysize.value) ||
      markForm.authorsize.value == "" || !checkNum(markForm.authorsize.value) ||
      markForm.sourcesize.value == "" || !checkNum(markForm.sourcesize.value))
  {
    alert("请填写正确的要选取的字数！");
    return;
  }
  if (markForm.listType.length == 0 || markForm.listType.selectedIndex == 0)
  {
    alert("请选取文章列表样式！");
    return;
  }
  if (markForm.days.value == "" || (markForm.days.value != "" && !checkNum(markForm.days.value)))
  {
    alert("请填写正确的新文章天数！");
    return;
  }
  if (markForm.days.value != "" && markForm.days.value != "0" && (markForm.seldays.length == 0 || markForm.seldays.selectedIndex == 0))
  {
    alert("请选取新文章样式文件！");
    return;
  }
  if (markForm.linkradio[1].checked && markForm.urlname.value == "")
  {
    alert("请填写自定义的URL地址！");
    return;
  }
  if (markForm.chineseName.value == "")
  {
    alert("请填写标记中文名称！");
    return;
  }
  if (markForm.notes.value.indexOf('[') > -1 || markForm.notes.value.indexOf(']') > -1 ||
      markForm.chineseName.value.indexOf('[') > -1 || markForm.chineseName.value.indexOf(']') > -1)
  {
    alert("请不要在标记名称及描述中输入 [ ] 括号！");
    return;
  }

  var attrVal = "[TAG]";
  if (type == 1)
  {
    if (markForm.article.value == "")
      attrVal = attrVal + "[SUBARTICLE_LIST][ARTICLEID]0[/ARTICLEID]";
    else
      attrVal = attrVal + "[SUBARTICLE_LIST][ARTICLEID]" + markForm.article.value + "[/ARTICLEID]";
  }
  else if (type == 2)
  {
    if (markForm.article.value == "")
      attrVal = attrVal + "[BROTHER_LIST][ARTICLEID]0[/ARTICLEID]";
    else
      attrVal = attrVal + "[BROTHER_LIST][ARTICLEID]" + markForm.article.value + "[/ARTICLEID]";
  }
  else
  {
    attrVal = attrVal + "[ARTICLE_LIST]";
  }
  columnlist = "[COLUMNS]";
  columnids = "[COLUMNIDS](";

  //选择使用那些的文章生成文章列表,如果没有选择栏目,则使用当前栏目的文章生成文章列表
  if (markForm.selectedColumn.length > 0)
  {
    for (var i=0; i < markForm.selectedColumn.length-1; i++)
    {
      var str = markForm.selectedColumn.options[i].text;
      var p = 0;
      if ((p=str.indexOf("-getAllSubArticle")) > -1)
      {
        str = str.substring(0, p);
        columnlist = columnlist + str + ",";
        columnids = columnids + markForm.selectedColumn.options[i].value + "-getAllSubArticle" + ",";
      }
      else
      {
        columnlist = columnlist + str + ",";
        columnids = columnids + markForm.selectedColumn.options[i].value + ",";
      }
    }

    var str = markForm.selectedColumn.options[markForm.selectedColumn.length-1].text;
    var p = 0;
    if ((p=str.indexOf("-getAllSubArticle")) > -1)
    {
      str = str.substring(0, p);
      columnlist = columnlist + str + "[/COLUMNS]";
      columnids = columnids + markForm.selectedColumn.options[markForm.selectedColumn.length-1].value + "-getAllSubArticle" + ")[/COLUMNIDS]";
    }
    else
    {
      columnlist = columnlist + str + "[/COLUMNS]";
      columnids = columnids + markForm.selectedColumn.options[markForm.selectedColumn.length-1].value + ")[/COLUMNIDS]";
    }
  }
  else
  {
    columnlist = columnlist + "*[/COLUMNS]";
    columnids = columnids + "0)[/COLUMNIDS]";
  }
  attrVal = attrVal + columnlist + columnids;

  //判断文章选择的方式
  if (markForm.selectArtList[0].checked)
  {
    attrVal = attrVal + "[SELECTWAY]0[/SELECTWAY]";
    attrVal = attrVal + "[ARTICLENUM]" + markForm.artNum.value + "[/ARTICLENUM]";
  }
  else
  {
    attrVal = attrVal + "[SELECTWAY]2[/SELECTWAY]";
    attrVal = attrVal + "[ARTICLENUM]" + markForm.articleNumInPage.value + "[/ARTICLENUM]";
    if (markForm.navPosition[0].checked)
      attrVal = attrVal + "[NAVBAR]" + markForm.navbar.value + "[/NAVBAR]";
    else
      attrVal = attrVal + "[NAVBAR]0[/NAVBAR]";
  }

  attrVal = attrVal + "[LISTTYPE]" + markForm.listType.value + "[/LISTTYPE]";
  attrVal = attrVal + "[LETTERNUM]" + markForm.titlesize.value + "[/LETTERNUM]";
  if (markForm.vicetitlesize.value != "" && checkNum(markForm.vicetitlesize.value) && markForm.vicetitlesize.value != "0")
    attrVal = attrVal + "[ARTICLE_VICETITLE]" + markForm.vicetitlesize.value + "[/ARTICLE_VICETITLE]";
  if (markForm.summarysize.value != "" && checkNum(markForm.summarysize.value) && markForm.summarysize.value != "0")
    attrVal = attrVal + "[ARTICLE_SUMMARY]" + markForm.summarysize.value + "[/ARTICLE_SUMMARY]";
  if (markForm.authorsize.value != "" && checkNum(markForm.authorsize.value) && markForm.authorsize.value != "0")
    attrVal = attrVal + "[ARTICLE_AUTHOR]" + markForm.authorsize.value + "[/ARTICLE_AUTHOR]";
  if (markForm.sourcesize.value != "" && checkNum(markForm.sourcesize.value) && markForm.sourcesize.value != "0")
    attrVal = attrVal + "[ARTICLE_SOURCE]" + markForm.sourcesize.value + "[/ARTICLE_SOURCE]";
  if (markForm.contentsize.value != "" && checkNum(markForm.contentsize.value) && markForm.contentsize.value != "0")
    attrVal = attrVal + "[ARTICLE_CONTENT]" + markForm.contentsize.value + "[/ARTICLE_CONTENT]";

  if (extendStr != "")
  {
    var extend = extendStr.split(",");
    for (var i=0; i<extend.length; i++)
    {
      var val = document.all(extend[i]).value;
      if (val != "" && checkNum(val) && val != "0")
        attrVal = attrVal + "[" + extend[i] + "]" + val + "[/" + extend[i] + "]";
    }
  }

  //文章显示顺序
  attrVal = attrVal + "[ORDER]" + markForm.order1.value + "," + markForm.order2.value + "," + markForm.order3.value + "[/ORDER]";

  attrVal = attrVal + "[ORDER_RANGE]";
  attrVal = attrVal + "[TIME_RANGE]" + markForm.time1.value + "," + markForm.time2.value + "[/TIME_RANGE]";
  attrVal = attrVal + "[POWER_RANGE]" + markForm.power1.value + "[/POWER_RANGE]";
  attrVal = attrVal + "[VICEPOWER_RANGE]" + markForm.power2.value + "[/VICEPOWER_RANGE]";
  attrVal = attrVal + "[NUMBER_RANGE]" + markForm.num1.value + "," + markForm.num2.value + "[/NUMBER_RANGE]";
  attrVal = attrVal + "[/ORDER_RANGE]";

  //被选中文章的标题在文章列表中的显示颜色
  attrVal = attrVal  + "[COLOR]" + markForm.sellink.value + "[/COLOR]";

  if (markForm.exclude.checked)
    attrVal = attrVal  + "[EXCLUDE]1[/EXCLUDE]";

  if (markForm.archive.checked)
    attrVal = attrVal  + "[ARCHIVE]1[/ARCHIVE]";
  if (markForm.allarticle.checked)
    attrVal = attrVal  + "[ARCHIVE]2[/ARCHIVE]";

  attrVal = attrVal + "[NEW]" + markForm.days.value + "[/NEW]";
  if (markForm.days.value != "" && markForm.days.value != "0")
    attrVal = attrVal + "[DAYSTYLE]" + markForm.seldays.value + "[/DAYSTYLE]";

  //连接的定义方式
  attrVal = attrVal + "[LINK]";
  if (markForm.linkradio[0].checked)
  {
    attrVal = attrVal + "[WAY]0[/WAY]";
  }
  else
  {
    attrVal = attrVal + "[WAY]1[/WAY]";
    attrVal = attrVal + "[URL]" + markForm.urlname.value + "[/URL]";
    attrVal = attrVal + "[PARAM]" + markForm.param.value + "[/PARAM]";
    if (markForm.aid.checked)
      attrVal = attrVal + "[AID]1[/AID]";
    else
      attrVal = attrVal + "[AID]0[/AID]";
    if (markForm.cid.checked)
      attrVal = attrVal + "[CID]1[/CID]";
    else
      attrVal = attrVal + "[CID]0[/CID]";
  }
  attrVal = attrVal + "[/LINK]";

  //是否将标记发布成包含文件
  if (markForm.innerFlag[0].checked)
    attrVal = attrVal + "[INNERHTMLFLAG]0[/INNERHTMLFLAG]";
  else
    attrVal = attrVal + "[INNERHTMLFLAG]1[/INNERHTMLFLAG]";

  attrVal = attrVal + "[CHINESENAME]" + markForm.chineseName.value + "[/CHINESENAME]";
  attrVal = attrVal + "[NOTES]" + markForm.notes.innerText + "[/NOTES]";

  if (type == 1)
    attrVal = attrVal + "[/SUBARTICLE_LIST][/TAG]";
  else if (type == 2)
    attrVal = attrVal + "[/BROTHER_LIST][/TAG]";
  else
    attrVal = attrVal + "[/ARTICLE_LIST][/TAG]";

  markForm.saveas.value = saveas;
  markForm.columnIDs.value = columnids;
  markForm.content.value = attrVal;
  markForm.submit();
}

function checkNum(str)
{
  var success = true;
  for (var i=0; i < str.length; i++)
  {
    if (str.substring(i,i+1) < '0' || str.substring(i,i+1) > '9')
    {
      success = false;
      break;
    }
  }
  return success;
}

function AddRelatedArticleID()
{
  var retval = showModalDialog("../article/addRelatedArticle.jsp","AddRelatedArticleID","font-family:Verdana; font-size:12; dialogWidth:55em; dialogHeight:35em; status=no");
  if (retval != "" && retval != undefined)
  {
    articleID.innerText = retval.substring(0, retval.indexOf(","));
    cname.innerText = retval.substring(retval.indexOf(",") + 1, retval.lastIndexOf(","));
    maintitle.innerText = retval.substring(retval.lastIndexOf(",") + 1);
    markForm.article.value = retval.substring(0, retval.indexOf(","));
  }
}

//以下是栏目列表
function activeArticle(activeflag)
{
  if (activeflag == 0)
  {
    markForm.urlname.disabled = true;
    markForm.linkArtNum.disabled = true;
    markForm.aid.disabled = true;
    markForm.cid.disabled = true;
    markForm.param.disabled = true;
  }
  if (activeflag == 1)
  {
    markForm.urlname.disabled = false;
    markForm.linkArtNum.disabled = true;
    markForm.aid.disabled = true;
    markForm.cid.disabled = false;
    markForm.param.disabled = false;
  }
  if (activeflag == 2)
  {
    markForm.urlname.disabled = true;
    markForm.linkArtNum.disabled = false;
    markForm.aid.disabled = false;
    markForm.cid.disabled = false;
    markForm.param.disabled = false;
  }
}

function createColumnList()
{
  if (markForm.listType.selectedIndex == 0)
  {
    alert("请选择栏目列表样式！");
    return;
  }
  if (markForm.linkradio[1].checked && markForm.urlname.value == "")
  {
    alert("请输入自定义URL名称！");
    return;
  }
  if (markForm.linkradio[2].checked && markForm.linkArtNum.value == "")
  {
    alert("请输入要连接的文章序号！");
    return;
  }
  if (markForm.linkradio[2].checked && !checkNum(markForm.linkArtNum.value))
  {
    alert("文章序号应为整数！");
    return;
  }
  if (markForm.chineseName.value == "")
  {
    alert("请输入标记中文名称！");
    return;
  }

  var attrVal = "[TAG][COLUMN_LIST]";
  columnlist = "[COLUMNS]";
  columnids = "[COLUMNIDS](";

  //选择使用那些的文章生成文章列表,如果没有选择栏目，则使用当前栏目的文章生成文章列表
  if (markForm.selectedColumn.length > 0)
  {
    for (var i=0; i<markForm.selectedColumn.length-1; i++)
    {
      columnlist = columnlist + markForm.selectedColumn.options[i].text + ",";
      columnids = columnids + markForm.selectedColumn.options[i].value + ",";
    }
    columnlist = columnlist + markForm.selectedColumn.options[markForm.selectedColumn.length-1].text + "[/COLUMNS]";
    columnids = columnids + markForm.selectedColumn.options[markForm.selectedColumn.length-1].value + ")[/COLUMNIDS]";
  }
  else
  {
    columnlist = columnlist + "*[/COLUMNS]";
    columnids = columnids + "0)[/COLUMNIDS]";
  }

  attrVal = attrVal + columnlist + columnids + "[LISTTYPE]" + markForm.listType.value + "[/LISTTYPE]";

  attrVal = attrVal + "[LINK]";
  if (markForm.linkradio[0].checked)
  {
    attrVal = attrVal + "[WAY]0[/WAY]";
  }
  if (markForm.linkradio[1].checked)
  {
    attrVal = attrVal + "[WAY]1[/WAY]";
    attrVal = attrVal + "[URL]" + markForm.urlname.value + "[/URL]";
    attrVal = attrVal + "[PARAM]" + markForm.param.value + "[/PARAM]";
    if (markForm.cid.checked)
      attrVal = attrVal + "[CID]1[/CID]";
    else
      attrVal = attrVal + "[CID]0[/CID]";
  }
  if (markForm.linkradio[2].checked)
  {
    attrVal = attrVal + "[WAY]2[/WAY]";
    attrVal = attrVal + "[LINKARTICLE]" + markForm.linkArtNum.value + "[/LINKARTICLE]";
    attrVal = attrVal + "[PARAM]" + markForm.param.value + "[/PARAM]";
    if (markForm.cid.checked)
      attrVal = attrVal + "[CID]1[/CID]";
    else
      attrVal = attrVal + "[CID]0[/CID]";
    if (markForm.aid.checked)
      attrVal = attrVal + "[AID]1[/AID]";
    else
      attrVal = attrVal + "[AID]0[/AID]";
  }
  attrVal = attrVal + "[/LINK]";

  if (markForm.innerFlag[0].checked)
    attrVal = attrVal + "[INNERHTMLFLAG]0[/INNERHTMLFLAG]";
  else
    attrVal = attrVal + "[INNERHTMLFLAG]1[/INNERHTMLFLAG]";

  attrVal = attrVal + "[CHINESENAME]" + markForm.chineseName.value + "[/CHINESENAME]";
  attrVal = attrVal + "[NOTES]" + markForm.notes.innerText + "[/NOTES]";
  attrVal = attrVal + "[/COLUMN_LIST][/TAG]";

  markForm.columnIDs.value = columnids;
  markForm.content.value = attrVal;
  markForm.submit();
}

//以下是相关文章列表
function selectArea(val)
{
  markForm.selectedColumn.disabled = val;
  markForm.deleteButton.disabled = val;
}

function createRelateList()
{
  if (markForm.keytype[1].checked && markForm.keyword.value == "")
  {
    alert("请填写要自定义的关键字！");
    return;
  }
  else if (markForm.artNum.value == "")
  {
    alert("请填写需要读取的相关文章数目！");
    return;
  }
  else if (!checkNum(markForm.artNum.value))
  {
    alert("请填写正确的相关文章数目！");
    return;
  }
  else if (markForm.letterNum.value == "")
  {
    alert("请填写标题需要显示的字数！");
    return;
  }
  else if (!checkNum(markForm.letterNum.value))
  {
    alert("请填写正确的标题显示字数！");
    return;
  }
  else if (markForm.listType.selectedIndex == 0)
  {
    alert("请选取相关文章列表样式文件！");
    return;
  }
  else if (markForm.chineseName.value == "")
  {
    alert("请填写列表中文名称！");
    return;
  }

  var attrVal = "[TAG][RELATED_ARTICLE]";
  columnlist = "[COLUMNS]";
  columnids = "[COLUMNIDS](";

  //选择使用那些的文章生成文章列表,如果没有选择栏目，则使用当前栏目的文章生成文章列表
  if (markForm.selectedColumn.length > 0 && markForm.area[1].checked)
  {
    for (var i=0; i<markForm.selectedColumn.length-1; i++)
    {
      var str = markForm.selectedColumn.options[i].text;
      var p = 0;
      if ((p=str.indexOf("-getAllSubArticle")) > -1)
      {
        str = str.substring(0, p);
        columnlist = columnlist + str + ",";
        columnids = columnids + markForm.selectedColumn.options[i].value + "-getAllSubArticle" + ",";
      }
      else
      {
        columnlist = columnlist + str + ",";
        columnids = columnids + markForm.selectedColumn.options[i].value + ",";
      }
    }

    var str = markForm.selectedColumn.options[markForm.selectedColumn.length-1].text;
    var p = 0;
    if ((p=str.indexOf("-getAllSubArticle")) > -1)
    {
      str = str.substring(0, p);
      columnlist = columnlist + str + "[/COLUMNS]";
      columnids = columnids + markForm.selectedColumn.options[markForm.selectedColumn.length-1].value + "-getAllSubArticle" + ")[/COLUMNIDS]";
    }
    else
    {
      columnlist = columnlist + str + "[/COLUMNS]";
      columnids = columnids + markForm.selectedColumn.options[markForm.selectedColumn.length-1].value + ")[/COLUMNIDS]";
    }
  }
  else if (markForm.area[1].checked)
  {
    columnlist = columnlist + "*[/COLUMNS]";
    columnids = columnids + "0)[/COLUMNIDS]";
  }

  if (markForm.keytype[0].checked)
    attrVal = attrVal + "[KEYWORD][/KEYWORD]";
  else
    attrVal = attrVal + "[KEYWORD]" + markForm.keyword.value + "[/KEYWORD]";

  if (markForm.area[0].checked)
    attrVal = attrVal + "[AREA]0[/AREA]";
  else
    attrVal = attrVal + columnlist + columnids + "[AREA]1[/AREA]";

  if (markForm.matchType[0].checked)
    attrVal = attrVal + "[MATCHTYPE]0[/MATCHTYPE]";
  else
    attrVal = attrVal + "[MATCHTYPE]1[/MATCHTYPE]";
  attrVal = attrVal + "[LISTTYPE]" + markForm.listType.value + "[/LISTTYPE]";
  attrVal = attrVal + "[ARTICLENUM]" + markForm.artNum.value + "[/ARTICLENUM]";
  attrVal = attrVal + "[LETTERNUM]" + markForm.letterNum.value + "[/LETTERNUM]";
  attrVal = attrVal + "[CHINESENAME]" + markForm.chineseName.value + "[/CHINESENAME]";
  attrVal = attrVal + "[NOTES]" + markForm.notes.innerText + "[/NOTES]";
  attrVal = attrVal + "[/RELATED_ARTICLE][/TAG]";

  markForm.content.value = attrVal;
  markForm.submit();
}

//以下是热点文章
function createTopStories(type)
{
  if (type == 0 && markForm.selectedArticle.length == 0)
  {
    alert("请选择至少一篇热点文章！");
    return;
  }
  if (markForm.listType.selectedIndex == 0)
  {
    alert("请选择热点文章样式文件！");
    return;
  }
  if (type == 1 && markForm.articleNum.value == "")
  {
    alert("请输入要显示的文章条数！");
    return;
  }
  if (type == 1 && !checkNum(markForm.articleNum.value))
  {
    alert("文章条数应为整数！");
    return;
  }
  if (type == 1 && (markForm.beginNum.value == "" || !checkNum(markForm.beginNum.value)))
  {
    alert("开始文章数不能为空并为整数！");
    return;
  }
  if (type == 1 && markForm.power.checked && markForm.power1.value == "" && markForm.power2.value == "")
  {
    alert("权重不能都为空！");
    return;
  }
  if (type == 1 && markForm.power.checked && ((markForm.power1.value != "" && !checkNum(markForm.power1.value)) || (markForm.power2.value != "" && !checkNum(markForm.power2.value))))
  {
    alert("权重应为整数！");
    return;
  }
  if (markForm.maintitle.value == "" || !checkNum(markForm.maintitle.value) ||
      markForm.vicetitle.value == "" || !checkNum(markForm.vicetitle.value) ||
      markForm.content.value == "" || !checkNum(markForm.content.value) ||
      markForm.author.value == "" || !checkNum(markForm.author.value) ||
      markForm.summary.value == "" || !checkNum(markForm.summary.value) ||
      markForm.source.value == "" || !checkNum(markForm.source.value))
  {
    alert("所有属性的字数不能为空并为整数！");
    return;
  }
  if (markForm.chineseName.value == "")
  {
    alert("请输入标记中文名称！");
    return;
  }
  if (markForm.chineseName.value != "" && (markForm.chineseName.value.indexOf("]") > -1 || markForm.chineseName.value.indexOf("[") > -1))
  {
    alert("标记中文名称不能包含 [ ] 符号！");
    return;
  }
  if (markForm.notes.value != "" && (markForm.notes.value.indexOf("]") > -1 || markForm.notes.value.indexOf("[") > -1))
  {
    alert("标记描述不能包含 [ ] 符号！");
    return;
  }

  var attrVal = "[TAG][TOP_STORIES]";
  attrVal = attrVal + "[LISTTYPE]" + markForm.listType.value + "[/LISTTYPE]";

  //选中的文章
  if (type == 0)
  {
    attrVal = attrVal + "[ARTICLE][ID]";
    for (var i=0; i<markForm.selectedArticle.length-1; i++)
    {
      attrVal = attrVal + markForm.selectedArticle.options[i].value + ",";
    }
    attrVal = attrVal + markForm.selectedArticle.options[markForm.selectedArticle.length-1].value + "[/ID][/ARTICLE]";

    if (markForm.order[0].checked)
      attrVal = attrVal + "[ORDER]0[/ORDER]";
    else
      attrVal = attrVal + "[ORDER]1[/ORDER]";
  }
  else
  {
    attrVal = attrVal + "[ARTICLE][ARTICLENUM]" + markForm.articleNum.value + "[/ARTICLENUM]";
    if (markForm.lastest.checked)
      attrVal = attrVal + "[LASTEST]1[/LASTEST]";
    else
      attrVal = attrVal + "[LASTEST]0[/LASTEST]";

    attrVal = attrVal + "[BEGINNUM]" + markForm.beginNum.value + "[/BEGINNUM]";

    if (markForm.power.checked)
      attrVal = attrVal + "[POWER]" + markForm.power1.value + "-" + markForm.power2.value + "[/POWER]"
    else
      attrVal = attrVal + "[POWER]-[/POWER]"
    attrVal = attrVal + "[/ARTICLE]";

    attrVal = attrVal + "[COLUMN][ID]";
    if (markForm.selectedColumn.length > 0)
    {
      for (var i=0; i<markForm.selectedColumn.length-1; i++)
        attrVal = attrVal + markForm.selectedColumn.options[i].value + ",";
      attrVal = attrVal + markForm.selectedColumn.options[markForm.selectedColumn.length-1].value + "[/ID]";
    }
    else
    {
      attrVal = attrVal + "0[/ID]";
    }

    attrVal = attrVal + "[NAME]";
    if (markForm.selectedColumn.length > 0)
    {
      for (var i=0; i<markForm.selectedColumn.length-1; i++)
        attrVal = attrVal + markForm.selectedColumn.options[i].text + ",";
      attrVal = attrVal + markForm.selectedColumn.options[markForm.selectedColumn.length-1].text + "[/NAME][/COLUMN]";
    }
    else
    {
      attrVal = attrVal + "*[/NAME][/COLUMN]";
    }
  }

  //字数
  attrVal = attrVal + "[ARTICLE_MAINTITLE]" + markForm.maintitle.value + "[/ARTICLE_MAINTITLE]";
  attrVal = attrVal + "[ARTICLE_VICETITLE]" + markForm.vicetitle.value + "[/ARTICLE_VICETITLE]";
  attrVal = attrVal + "[ARTICLE_CONTENT]" + markForm.content.value + "[/ARTICLE_CONTENT]";
  attrVal = attrVal + "[ARTICLE_SOURCE]" + markForm.source.value + "[/ARTICLE_SOURCE]";
  attrVal = attrVal + "[ARTICLE_SUMMARY]" + markForm.summary.value + "[/ARTICLE_SUMMARY]";
  attrVal = attrVal + "[ARTICLE_AUTHOR]" + markForm.author.value + "[/ARTICLE_AUTHOR]";
  attrVal = attrVal + "[ARTICLE_TIME]" + markForm.datestyle.value + "," + markForm.timestyle.value + "[/ARTICLE_TIME]";

  if (markForm.innerFlag[0].checked)
    attrVal = attrVal + "[INNERHTMLFLAG]0[/INNERHTMLFLAG]";
  else
    attrVal = attrVal + "[INNERHTMLFLAG]1[/INNERHTMLFLAG]";

  attrVal = attrVal + "[CHINESENAME]" + markForm.chineseName.value + "[/CHINESENAME][NOTES]" + markForm.notes.value + "[/NOTES]";
  attrVal = attrVal + "[/TOP_STORIES][/TAG]";

  markForm.contents.value = attrVal;
  markForm.submit();
}

//以下是子文章条数
function createArticleCountMark(type)
{
  if (markForm.time1.value != "" && markForm.time2.value != "")
  {
    var t1 = markForm.time1.value;
    var d1 = new Date();
    d1.setYear(t1.substring(0, 4));
    d1.setMonth(parseInt(t1.substring(5,7)) - 1);
    d1.setDate(t1.substring(8, 10));

    var t2 = markForm.time2.value;
    var d2 = new Date();
    d2.setYear(t2.substring(0, 4));
    d2.setMonth(parseInt(t2.substring(5,7)) - 1);
    d2.setDate(t2.substring(8, 10));

    if (d1.getTime() > d2.getTime())
    {
      alert("开始时间应比结束时间小！");
      return;
    }
  }
  if (markForm.power1.value != "" || markForm.power2.value != "")
  {
    if ((markForm.power1.value != "" && !checkNum(markForm.power1.value)) || (markForm.power2.value != "" && !checkNum(markForm.power2.value)))
    {
      alert("权重应为整数！");
      return;
    }
    if (markForm.power1.value != "" && markForm.power2.value != "" && parseInt(markForm.power1.value) > parseInt(markForm.power2.value))
    {
      alert("开始权重应比结束权重小！");
      return;
    }
  }
  if (markForm.num1.value != "" || markForm.num2.value != "")
  {
    if ((markForm.num1.value != "" && !checkNum(markForm.num1.value)) || (markForm.num2.value != "" && !checkNum(markForm.num2.value)))
    {
      alert("序号应为整数！");
      return;
    }
    if (markForm.num1.value != "" && markForm.num2.value != "" && parseInt(markForm.num1.value) > parseInt(markForm.num2.value))
    {
      alert("开始序号应比结束序号小！");
      return;
    }
  }

  var attrVal = "[TAG][ARTICLE_COUNT]";
  if (type == 1)  attrVal = "[TAG][SUBARTICLE_COUNT]";

  columnlist = "[COLUMNS]";
  columnids = "[COLUMNIDS](";

  if (markForm.selectedColumn.length > 0)
  {
    for (var i=0; i < markForm.selectedColumn.length-1; i++)
    {
      var str = markForm.selectedColumn.options[i].text;
      var p = 0;
      if ((p=str.indexOf("-getAllSubArticle")) > -1)
      {
        str = str.substring(0, p);
        columnlist = columnlist + str + ",";
        columnids = columnids + markForm.selectedColumn.options[i].value + "-getAllSubArticle" + ",";
      }
      else
      {
        columnlist = columnlist + str + ",";
        columnids = columnids + markForm.selectedColumn.options[i].value + ",";
      }
    }

    var str = markForm.selectedColumn.options[markForm.selectedColumn.length-1].text;
    var p = 0;
    if ((p=str.indexOf("-getAllSubArticle")) > -1)
    {
      str = str.substring(0, p);
      columnlist = columnlist + str + "[/COLUMNS]";
      columnids = columnids + markForm.selectedColumn.options[markForm.selectedColumn.length-1].value + "-getAllSubArticle" + ")[/COLUMNIDS]";
    }
    else
    {
      columnlist = columnlist + str + "[/COLUMNS]";
      columnids = columnids + markForm.selectedColumn.options[markForm.selectedColumn.length-1].value + ")[/COLUMNIDS]";
    }
  }
  else
  {
    columnlist = columnlist + "*[/COLUMNS]";
    columnids = columnids + "0)[/COLUMNIDS]";
  }
  attrVal = attrVal + columnlist + columnids;

  attrVal = attrVal + "[TIME_RANGE]" + markForm.time1.value + "," + markForm.time2.value + "[/TIME_RANGE]";
  attrVal = attrVal + "[POWER_RANGE]" + markForm.power1.value + "," + markForm.power2.value + "[/POWER_RANGE]";
  attrVal = attrVal + "[ORDER_RANGE]" + markForm.num1.value + "," + markForm.num2.value + "[/ORDER_RANGE]";

  if (type == 1)
  {
    if (markForm.article.value == "")
      attrVal = attrVal + "[ARTICLEID]0[/ARTICLEID][/SUBARTICLE_COUNT][/TAG]";
    else
      attrVal = attrVal + "[ARTICLEID]" + markForm.article.value + "[/ARTICLEID][/SUBARTICLE_COUNT][/TAG]";
  }
  else
  {
    attrVal = attrVal + "[/ARTICLE_COUNT][/TAG]";
  }

  window.returnValue = attrVal;
  window.close();
}