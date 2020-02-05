var tLen = 0;
var sLen = 0;
var lk_ds,server = '',keywordList = new Array(),lk_bW = new Array(),aAD = new Array(),ulS = '',lk_dk = '',lk_dQ_linkool_iTt;
var lk_dE = (document.all)?true:false;
var displayMethod = 11;
var lk_zX = new Array();
var lk_uF = false;
var lk_dx = null;
var alk_aT = false;
var alk_aV = false;
var alk_aS = false;
var alk_aU = false;
var alk_bS = false;
var alk_bV = false;
var alk_bR = false;
var alk_bU = false;
var MIN_SPACE_BETWEEN_WORDS_CE = -1;
var MAX_KEYWORD_NUM_DISPLAY_CE = 10;
var MAX_LENGTH_CE = 10000;
var alk_br = 0;
var alk_cq = "";
var EXTAGS = "";
function init_clickeye() {
    if (document.body == null) {
        setTimeout("init_clickeye()", 10000);
        return;
    }
    var alk_cr = navigator.appName.toLowerCase();
    var alk_bm = alk_cr.indexOf("msie 7") > -1;
    if (lk_dE && !alk_bm) {
        try {
            document.execCommand("BackgroundImageCache", false, true);
        } catch(e) {
        }
    }
    lk_dQ_linkool_iTt = document.getElementById('lk_dQ_linkool_iTt');
    if (null == lk_dQ_linkool_iTt) {
        var lk_dO = document.createElement('span');
        lk_dO.id = 'lk_dQ_linkool_iTt';
        lk_dO.className = 'lk_dQ_linkool_iTt';
        lk_dO.style.visibility = 'hidden';
        lk_dO.style.display = 'none';
        lk_dO.style.position = 'absolute';
        lk_dO.onmouseover = lk_bh;
        lk_dO.onmouseout = lk_bj;
        lk_dO.style.zIndex = 1000;
        try {
            var lk_cc;
            lk_cc = lk_zE(document.body);
            var alk_bx = document.createElement('div');
            alk_bx.style.visibility = 'hidden';
            lk_dQ_linkool_iTt = document.body.insertBefore(alk_bx, lk_cc[0]);
            lk_cc = lk_zE(document.body);
            lk_dQ_linkool_iTt = document.body.insertBefore(lk_dO, lk_cc[0]);
        } catch(e) {
            return;
        }
        lk_aO();
        return;
    }
}
;
function lk_aO() {
    try {
        alk_ai();
        lk_av();
    } catch(e) {
    }
    var dll = false;
    try {
        for (var i = 0; i < aAD.length; i++) {
            if (aAD[i][0].alk_cn != "undefined" && aAD[i][0].alk_cn == 20) {
                dll = true;
                break;
            }
        }
        if (dll) {
            loadDLLScript();
            DLLLoaded = true;
        }
    } catch(xx) {
    }
}
;
function alk_ai() {
    if (typeof setulS != 'undefined')setulS(); else ulS = 'text-decoration:underline;color:#6600ff;background-color:transparent;border-bottom: 1px dotted #6600ff;';
    LoadClickEyeAds();
    if (MIN_SPACE_BETWEEN_WORDS_CE < 0)MIN_SPACE_BETWEEN_WORDS_CE = ((keywordList.length - 2) * 10) > MIN_SPACE_BETWEEN_WORDS_CE?((keywordList.length - 2) * 10):MIN_SPACE_BETWEEN_WORDS_CE;
    if (MIN_SPACE_BETWEEN_WORDS_CE > 100)MIN_SPACE_BETWEEN_WORDS_CE = 100;
}
;
function lk_av() {
    lk_dx = getElementsByClass();
    if (lk_dx != null && lk_dx.length > 0) {
        fH_2();
        window.setTimeout(lk_aK, 100);
    }
}
;
function fH_2() {
    var lk_eX = MIN_SPACE_BETWEEN_WORDS_CE;
    var lk_fA = new Array();
    var max_key = 5;
    if (typeof MAX_KEYWORD_NUM_DISPLAY_CE != "undefined") {
        max_key = MAX_KEYWORD_NUM_DISPLAY_CE;
    }
    var lk_full = false;
    var lk_ca = new Array();
    for (var f = 0; lk_full == false && f < keywordList.length; f++) {
        for (var i = 0; i < lk_dx.length; i++) {
            var found = fH(lk_bW, i, keywordList[f], lk_dx[i], lk_fA, lk_eX, f);
            if (found) {
                lk_ca[lk_ca.length] = keywordList[f];
                break;
            }
            if (lk_bW.length > max_key) {
                lk_full = true;
                break;
            }
        }
    }
}
;
function fH(lk_bW, i, kword, b, lk_fA, lk_eX, keyIdx) {
    var lk_gp = (typeof(ScriptEngineMajorVersion) == "function"?Number(ScriptEngineMajorVersion() + '.' + ScriptEngineMinorVersion()):5.5);
    if (b == null)return;
    var body = lk_escH(b.data);
    if (body == null)return;
    if (lk_ay(lk_bW, kword, i) != null)return;
    if (window.location.hostname.indexOf("qq.com") > 0 && isQLks(kword))return;
    var lk_dM,lk_fK = new RegExp("[a-zA-Z0-9]"),lk_fG,lk_cg = 0,alk_bX,alk_bY,alk_bZ,lk_fm,lk_fn,t = body;
    if (lk_fK.exec(kword.substr(0, 1)) && lk_fK.exec(kword.substr(kword.length - 1, 1))) {
        lk_fG = new RegExp('[^<]\\b(' + kword + ')\\b|\\b(' + kword + ')\\b[^>]', 'gi' + (lk_gp >= 5.5?'m':''));
    } else {
        lk_fG = new RegExp('(' + kword + ')', 'gi' + (lk_gp >= 5.5?'m':''));
    }
    while ((alk_bX = lk_fG.exec(t)) && !lk_cg) {
        lk_cg = 1;
        lk_fm = lk_aX(lk_fG, alk_bX);
        lk_dM = alk_bX[1].length;
        lk_fn = lk_fm - lk_dM;
        lk_fB = new lk_bd(lk_fn + body.length - t.length, lk_fm + body.length - t.length, i);
        if (lk_aU(lk_fA, lk_fB, b, lk_eX)) {
            lk_cg = 0;
        } else {
            lk_fA[lk_fA.length] = lk_fB;
        }
        if (lk_cg) {
            lk_fn += (body.length - t.length);
            lk_cg = !lk_aM(i, lk_bW, lk_fn, lk_fn + lk_dM);
        }
        t = t.substring(lk_fm);
        lk_fG.lastIndex = 0;
    }
    if (lk_cg)lk_bW[lk_bW.length] = new lk_aL(i, kword, lk_fn, lk_fn + lk_dM, keyIdx);
    return lk_cg;
}
;
function lk_aK() {
    alk_z();
    alk_B();
    lk_aJ(aAD, lk_bW);
}
;
function lk_bd(f, e, i) {
    this.f = f;
    this.e = e;
    this.i = i;
}
;
function lk_aU(a, p, b, lk_eX) {
    if (a.length == 0)return false;
    for (var i = a.length - 1; i >= 0; i--) {
        var alk_ay = a[i];
        if (alk_ay.i == p.i) {
            if (b.data != null && (Math.max(alk_ay.f, p.f) - Math.min(alk_ay.e, p.e)) < lk_eX) {
                return true;
            }
        } else if (alk_ay.i < p.i) {
            var alk_aR = lk_dx[alk_ay.i].data.length - alk_ay.e + p.f;
            for (var alk_bK = alk_ay.i + 1; alk_bK < p.i; alk_bK++) {
                alk_aR += lk_dx[alk_bK].data.length;
            }
            if (alk_aR < lk_eX)return true;
        } else if (alk_ay.i > p.i) {
            var alk_aR = lk_dx[p.i].data.length - p.e + alk_ay.f;
            for (var alk_bK = p.i + 1; alk_bK < alk_ay.i; alk_bK++) {
                alk_aR += lk_dx[alk_bK].data.length;
            }
            if (alk_aR < lk_eX)return true;
        }
    }
    return false;
}
;
function bx(x, y, w, h) {
    this.l = x;
    this.r = x + w;
    this.t = y;
    this.b = y + h;
}
;
function lk_ap(o) {
    var b = new bx(0, 0, 0, 0);
    if (!o)return b;
    var x = 0,y = 0,p = o;
    while (p) {
        x += p.offsetLeft;
        y += p.offsetTop;
        p = p.offsetParent;
    }
    b.l = x;
    b.t = y;
    if (!lk_dE && o.innerHTML != null && o.innerHTML.toLowerCase().indexOf(".swf") > 0) {
        if (o.width != null)b.r = x + parseInt(o.width); else b.r = x + o.offsetWidth;
        if (o.height != null)b.b = y + parseInt(o.height); else b.b = y + o.offsetHeight;
    } else {
        b.r = x + o.offsetWidth;
        b.b = y + o.offsetHeight;
    }
    return b;
}
;
function kwC() {
    if (lk_dk.length > 0) {
        var lk_gl = lk_aG(lk_dk);
        window.open(lk_gl, '_blank');
    }
    return false;
}
;
function lk_bk(lk_cA, d, index, evt) {
    try {
        var a = lk_ax(aAD, d, index);
        if (a == null)return;
        if (a.alk_cn == 20) {
            try {
                lk_dQ_linkool_iTt.style.visibility = 'hidden';
                lk_dQ_linkool_iTt.style.display = 'none';
                lk_dQ_linkool_iTt.innerHTML = '';
                if (!DLLLoaded) {
                    loadDLLScript();
                    DLLLoaded = true;
                }
                ppc.over(evt, a.k, a.tt);
            } catch(x_x) {
            }
            if (lk_zX["" + a.lk_cy] == null) {
                alk_cq = "" + a.lk_cy;
                lk_zX[alk_cq] = alk_cq;
                lk_aY('i', "", 0);
            }
            lk_dk = "http://www.dilingling.com/?eye=1&q=" + a.k + "&op=search";
            return;
        }
        var lk_fP = "";
        if (a.lk_dD) {
            var lk_cK = true;
            for (var k = 0; k < a.al.length; k++) {
                if (0 < a.al[k].lk_cy) {
                    lk_fP = a.al[k].a;
                    lk_cK = false;
                    break;
                }
            }
            if (lk_cK)lk_fP = a.al[(a.al.length - 1)].a
        } else {
            lk_fP = a.a;
        }
        lk_dk = lk_fP;
        lk_dQ_linkool_iTt.style.display = 'none';
        alk_u(lk_cA, evt);
    } catch(e) {
    }
}
;
function lk_bh() {
    alk_v();
}
;
function lk_bj() {
    alk_w();
}
;
function kwE(e, d, text) {
    try {
        alk_S();
        alk_L(d);
        alk_br = d;
        e = (e)?e:((window.event)?window.event:null);
        if (e == null)return;
        var lk_cA = (e.target)?e.target:e.srcElement;
        lk_bk(lk_cA, d, 0, e);
        if (window.location.hostname.indexOf("qq.com") < 0) {
            text.style.textDecoration = "underline";
            text.style.borderBottom = "2px dotted";
        }
    } catch(e) {
    }
}
;
function kwM(d) {
    try {
        var lk_cb = lk_ax(aAD, d, 0);
        if (lk_cb == null)return;
        var lk_gd = (lk_cb.t == '')?"ClickEye":lk_cb.t;
        window.status = lk_gd.replace(/\&pound\;/, '?');
    } catch(e) {
    }
}
;
function kwL(e, text) {
    try {
        alk_t();
        var a = aAD[alk_br][0];
        if (a == null)return;
        if (a.alk_cn == 20) {
            ppc.out(e);
        }
    } catch(e) {
    }
}
;
function lk_bl(s) {
    var lk_fL = /\&\#39\;/gi;
    s = s.replace(lk_fL, "'");
    return s;
}
;
function lk_aL(i, k, s, e) {
    this.i = i;
    this.k = lk_bl(k);
    this.s = s;
    this.e = e;
}
;
function iA(d, k, t, tt, c, a, co, alt, f, lk_dK, lk_gr, lk_gs, lk_gj, lk_gk, lk_cG, alk_cs, alk_az, alk_cn) {
    this.lk_cy = d;
    this.k = lk_bl(k);
    this.t = lk_bl(t);
    this.tt = lk_bl(tt);
    this.c = c;
    if (d <= 0 && window.location.hostname.indexOf("qihoo.com") > 0)this.a = "http://" + window.location.hostname + "/forum/article/article.php?u=" + lk_am(a.replace(/\%2e/g, '.')) + "&title=" + tt + "&t=7&pjt=bbs2"; else this.a = "http://www230.clickeye.cn/redir.html?url=" + lk_am(a.replace(/\%2e/g, '.'));
    this.co = co;
    this.alt = alt;
    this.v = 0;
    this.lk_dD = false;
    this.f = f;
    this.lk_dK = lk_dK;
    this.lk_gr = lk_gr;
    this.lk_gs = lk_gs;
    this.lk_gj = lk_gj;
    this.lk_gk = lk_gk;
    this.lk_cG = lk_cG;
    this.alk_cs = alk_cs;
    this.alk_az = alk_az;
    this.alk_cn = alk_cn;
    if (c.length > 5 && this.t.length == 0 && this.tt.length > 0)this.t = this.tt;
    var alk_aB = 17;
    if (arguments.length > (alk_aB + 1)) {
        this.lk_dD = true;
        this.al = new Array(arguments.length - alk_aB);
        for (i = 0; i < arguments.length - alk_aB; i++) {
            if (i == 0) {
                this.al[i] = this;
            } else {
                this.al[i] = arguments[i + alk_aB];
                if (this.al[i].c.length > 5 && this.al[i].t.length == 0 && this.al[i].tt.length > 0)this.al[i].t = this.al[i].tt;
            }
        }
        this.s = arguments.length - alk_aB;
    }
    this.lk_fY = function() {
        if (this.lk_dD == true) {
            return String();
        } else {
            return;
        }
    };
    this.add = function(lk_dy) {
        if (this.lk_dD == true) {
            this.al[this.al.length] = lk_dy;
            this.s++;
        } else {
            this.lk_dD = true;
            this.al = new Array();
            this.al[0] = this;
            this.al[1] = lk_dy;
            this.s = this.al.length;
        }
    }
}
;
function lk_aM(i, h, s, e) {
    for (var j = 0; j < h.length; j++)if (h[j].i == i)if ((s >= h[j].s) && (s <= h[j].e))return true; else if ((e >= h[j].s) && (e <= h[j].e))return true; else if ((s <= h[j].s) && (e >= h[j].e))return true;
    return false;
}
;
function lk_aX(r, a) {
    return(typeof(a.lastIndex) == 'undefined'?r.lastIndex:a.lastIndex);
}
;
function lk_ay(a, k, f) {
    for (var i = 0; i < a.length; i++) {
        if (a[i].k == k)return a[i];
    }
    return null;
}
;
function lk_ax(a, d, index) {
    if (typeof a[d][index] != 'undefined')return a[d][index]; else return a[d][0];
}
;
function fABDID3(a) {
    for (var i = 0; i < lk_bW.length; i++)if (lk_bW[i].k == a.k)return i;
    return null;
}
;
function lk_aZ(a, i, s, v) {
    for (var f = 0; f < a.length; f++)if (a[f].i == i)if (a[f].s > s) {
        a[f].s += v;
        a[f].e += v;
    }
}
;
var alk_bn = 0;
function lk_aJ(a, r) {
    try {
        var c = 0;
        if (lk_dx == null || lk_dx.length == 0)return;
        var aH = new Array(lk_dx.length);
        for (var i = 0; i < aH.length; i++)aH[i] = null;
        var lk_eW = 5;
        if (typeof MAX_KEYWORD_NUM_DISPLAY_CE != "undefined") {
            lk_eW = MAX_KEYWORD_NUM_DISPLAY_CE;
        }
        var lk_ca = new Array();
        var lk_dJ = 0;
        for (var i = 0; i < a.length; i++) {
            if (lk_dJ >= lk_eW)break;
            var ad = a[i][0],alk_bb = lk_ay(r, ad.k, ad.f);
            if (alk_bb != null) {
                lk_dJ++;
                var h = aH[alk_bb.i];
                if (h == null) {
                    if (lk_dx.length > 1)h = lk_dx[alk_bb.i].data; else h = lk_dx[0].data;
                    h = lk_escH(h);
                }
                var lk_ge = h.substring(alk_bb.s, alk_bb.e),s;
                if (alk_bb.k.toLowerCase() == lk_ge.toLowerCase()) {
                    s = '<nobr id="key' + alk_bn + '"style="' + ulS + '" onclick="return kwC();" target="_blank" oncontextmenu="return false;"  onmouseover="kwE(event,' + i + ', this);" onmouseout="kwL(event, this);" onmousemove="kwM(' + i + ');">' + lk_ge + '</nobr>';
                    h = h.substring(0, alk_bb.s) + s + h.substring(alk_bb.e);
                    lk_aZ(r, alk_bb.i, alk_bb.e, s.length - (alk_bb.e - alk_bb.s));
                    aH[alk_bb.i] = h;
                    c++;
                    alk_bn++;
                }
            }
        }
        if (c > 0) {
            for (var i = 0; i < aH.length; i++) {
                try {
                    if (aH[i] != null) {
                        var alk_ck = document.createElement("CLK");
                        var ss = aH[i];
                        if (ss.charAt(0) == " ")ss = "&nbsp;" + ss;
                        alk_ck.innerHTML = ss;
                        if (window.location.href.indexOf(".people.com.cn") < 0) {
                            var tempParentNode = lk_dx[i].parentNode;
                            var tempChild = alk_ck.childNodes[0];
                            lk_dx[i].parentNode.replaceChild(alk_ck.childNodes[0], lk_dx[i]);
                            for (var childIdx = 0; childIdx < alk_ck.childNodes.length; childIdx) {
                                var tempChildNum = getChildIndex(tempParentNode, tempChild);
                                if (tempChildNum < tempParentNode.childNodes.length - 1) {
                                    var tempChild = alk_ck.childNodes[0];
                                    tempParentNode.insertBefore(alk_ck.childNodes[0], tempParentNode.childNodes[tempChildNum + 1]);
                                } else {
                                    var tempChild = alk_ck.childNodes[0];
                                    tempParentNode.appendChild(alk_ck.childNodes[0]);
                                }
                            }
                        } else {
                            lk_dx[i].parentNode.replaceChild(alk_ck, lk_dx[i]);
                        }
                    }
                } catch(e) {
                }
            }
        }
    } catch(alk_aW) {
    }
}
;
function getChildIndex(parent, alk_aF) {
    for (var i = 0; i < parent.childNodes.length; i++) {
        if (parent.childNodes[i] == alk_aF)return i;
    }
}
;
function lk_vX(lk_fCE) {
    if (lk_fCE == null)return;
    if (!alk_bV || ((lk_ds.l + lk_dQ_linkool_iTt.clientWidth + 10) > lk_fCE.l && (lk_ds.l - 10) < lk_fCE.r && (lk_ds.t + 20) > lk_fCE.t && (lk_ds.t - lk_dQ_linkool_iTt.clientHeight - 20) < lk_fCE.b)) {
        alk_aV = true;
    } else {
        return;
    }
    if (!alk_bS || ((lk_ds.l - lk_dQ_linkool_iTt.clientWidth - 10) < lk_fCE.r && (lk_ds.l + 10) > lk_fCE.l && (lk_ds.t + 20) > lk_fCE.t && (lk_ds.t - lk_dQ_linkool_iTt.clientHeight - 20) < lk_fCE.b)) {
        alk_aT = true;
    } else {
        return;
    }
    if (!alk_bU || ((lk_ds.l + lk_dQ_linkool_iTt.clientWidth + 10) > lk_fCE.l && (lk_ds.l - 10) < lk_fCE.r && (lk_ds.t - 20) < lk_fCE.b && (lk_ds.t + lk_dQ_linkool_iTt.clientHeight - 20) > lk_fCE.t)) {
        alk_aU = true;
    } else {
        return;
    }
    if (!alk_bR || ((lk_ds.l - lk_dQ_linkool_iTt.clientWidth - 10) < lk_fCE.r && (lk_ds.l + 10) > lk_fCE.l && (lk_ds.t - 20) < lk_fCE.b && (lk_ds.t + lk_dQ_linkool_iTt.clientHeight - 20) > lk_fCE.t)) {
        alk_aT = true;
    } else {
        return;
    }
}
;
function lk_bb() {
    alk_bS = ((lk_ds.l - 20 - lk_dQ_linkool_iTt.clientWidth - lk_aE()) > 0) && ((lk_ds.t - 20 - lk_dQ_linkool_iTt.clientHeight - lk_aF()) > 0);
    alk_bV = ((lk_ds.l + 20 + lk_dQ_linkool_iTt.clientWidth - lk_aE()) < lk_aA()) && ((lk_ds.t - 20 - lk_dQ_linkool_iTt.clientHeight - lk_aF()) > 0);
    alk_bR = ((lk_ds.l - 20 - lk_dQ_linkool_iTt.clientWidth - lk_aE()) > 0) && ((lk_aF() + lk_aC() - lk_ds.t - 20 - lk_dQ_linkool_iTt.clientHeight) > 0);
    alk_bU = ((lk_ds.l + 20 + lk_dQ_linkool_iTt.clientWidth - lk_aE()) < lk_aA()) && ((lk_aF() + lk_aC() - lk_ds.t - 20 - lk_dQ_linkool_iTt.clientHeight) > 0);
    lk_uF = false;
    var alk_ba = document.getElementsByTagName("object");
    for (var i = 0; i < alk_ba.length; i++) {
        var lk_fCE = lk_ap(alk_ba[i]);
        if (lk_fCE == null || lk_ds.l + lk_dQ_linkool_iTt.clientWidth + 30 < lk_fCE.l || lk_ds.l - lk_dQ_linkool_iTt.clientWidth - 30 > lk_fCE.r || lk_ds.t + lk_dQ_linkool_iTt.clientHeight + 30 < lk_fCE.t || lk_ds.t - lk_dQ_linkool_iTt.clientHeight - 30 > lk_fCE.b)continue;
        var alk_cx = alk_ba[i].innerHTML.toLowerCase();
        if (alk_cx.indexOf(".swf") > 0 && (alk_cx.indexOf("wmode") < 0 || (alk_cx.indexOf("opaque") < 0 && alk_cx.indexOf("transparent") < 0)))lk_vX(lk_fCE);
    }
    alk_ba = document.getElementsByTagName("iframe");
    for (var i = 0; i < alk_ba.length; i++) {
        var frame = alk_ba[i];
        var lk_fCE = lk_ap(frame);
        if (lk_fCE == null || lk_ds.l + lk_dQ_linkool_iTt.clientWidth + 30 < lk_fCE.l || lk_ds.l - lk_dQ_linkool_iTt.clientWidth - 30 > lk_fCE.r || lk_ds.t + lk_dQ_linkool_iTt.clientHeight + 30 < lk_fCE.t || lk_ds.t - lk_dQ_linkool_iTt.clientHeight - 30 > lk_fCE.b)continue;
        lk_vX(lk_fCE);
    }
    if (!alk_aV && alk_bV) {
        lk_dQ_linkool_iTt.style.left = (lk_ds.l + 10) + "px";
        lk_dQ_linkool_iTt.style.top = (lk_ds.t - 18 - lk_dQ_linkool_iTt.clientHeight) + "px";
    } else if (!alk_aT && alk_bS) {
        lk_dQ_linkool_iTt.style.left = (lk_ds.l + 10 - lk_dQ_linkool_iTt.clientWidth) + "px";
        lk_dQ_linkool_iTt.style.top = (lk_ds.t - 18 - lk_dQ_linkool_iTt.clientHeight) + "px";
    } else if (!alk_aU && alk_bU) {
        lk_dQ_linkool_iTt.style.left = (lk_ds.l + 10) + "px";
        lk_dQ_linkool_iTt.style.top = (lk_ds.b + 10) + "px";
    } else if (!alk_aS && alk_bR) {
        lk_dQ_linkool_iTt.style.left = (lk_ds.l + 10 - lk_dQ_linkool_iTt.clientWidth) + "px";
        lk_dQ_linkool_iTt.style.top = (lk_ds.b + 10) + "px";
    } else {
        lk_uF = true;
        if (alk_bV) {
            lk_dQ_linkool_iTt.style.left = (lk_ds.l + 10) + "px";
            lk_dQ_linkool_iTt.style.top = (lk_ds.t - 18 - lk_dQ_linkool_iTt.clientHeight) + "px";
        } else if (alk_bS) {
            lk_dQ_linkool_iTt.style.left = (lk_ds.l + 10 - lk_dQ_linkool_iTt.clientWidth) + "px";
            lk_dQ_linkool_iTt.style.top = (lk_ds.t - 18 - lk_dQ_linkool_iTt.clientHeight) + "px";
        } else if (alk_bR) {
            lk_dQ_linkool_iTt.style.left = (lk_ds.l + 10 - lk_dQ_linkool_iTt.clientWidth) + "px";
            lk_dQ_linkool_iTt.style.top = (lk_ds.b + 10) + "px";
        } else {
            lk_dQ_linkool_iTt.style.left = (lk_ds.l + 10) + "px";
            lk_dQ_linkool_iTt.style.top = (lk_ds.b + 10) + "px";
        }
    }
    alk_aT = false;
    alk_aV = false;
    alk_aS = false;
    alk_aU = false;
}
;
function lk_at(e, lk_fF, lk_cn, lk_cA, alk_bL, index) {
    lk_ba(e, lk_fF, lk_cn, lk_cA, alk_bL, index);
}
;
function lk_ba(e, u, lk_cn, lk_cA, alk_bL, index) {
    if (u == "" || u == "null" || u == "default.htm")return false;
    try {
        e = (e)?e:((window.event)?window.event:null);
        e.cancelBubble = true;
        if (alk_bL != null && alk_bL == true && !lk_dE)lk_aG(u, lk_cn, lk_cA, index); else window.open(lk_aG(u, lk_cn, lk_cA, index), '_blank');
    } catch(ex) {
    }
    return false;
}
;
function lk_aG(alk_cv, lk_cn, lk_cA, index) {
    var lk_gn = alk_cv;
    try {
        lk_aY('c', alk_cv, index);
    } catch(e) {
    }
    return lk_gn;
}
;
function lk_aY(action, alk_cv, index) {
    var a = lk_ax(aAD, alk_br, index);
    var e = new Image();
    e.width = '1';
    e.height = '1';
    if (a != null) {
        if (action == 'i') {
            if (!a.v) {
                var alk_bM = "";
                var al = a.al;
                alk_bM = server + "?keyid=" + alk_cq + "&channelId=" + encodeURI(a.alk_cs) + "&keyword=" + encodeURI(a.k) + "&action=" + action;
                e.src = alk_bM;
                alk_cq = "";
            }
        } else if (action == 'c') {
            var alk_ca = a.lk_cy;
            var alk_bp = a.k;
            if (a.lk_dD) {
                var al = a.al;
                for (i = 0; i < al.length; i++) {
                    if (al[i].a == alk_cv) {
                        alk_ca = al[i].lk_cy;
                        alk_az = al[i].alk_az;
                        break;
                    }
                }
            } else {
                alk_ca = a.lk_cy;
                alk_az = a.alk_az;
            }
            alk_bM = server + "?keyid=" + alk_ca + "&channelId=" + a.alk_cs + "&keyword=" + encodeURI(alk_bp) + "&action=" + action + "&url=" + lk_am(alk_cv);
            e.src = alk_bM;
        }
    }
}
;
function lk_am(lk_fU) {
    return escape(lk_fU).replace(/\+/g, '%2B').replace(/\"/g, '%22').replace(/\'/g, '%27').replace(/\//g, '%2F');
}
;
function lk_aF() {
    if (document.documentElement && document.documentElement.scrollTop)return document.documentElement.scrollTop; else if (document.body)return document.body.scrollTop; else return 0;
}
;
function lk_aE() {
    if (document.documentElement && document.documentElement.scrollLeft)return document.documentElement.scrollLeft; else if (document.body)return document.body.scrollLeft; else return 0;
}
;
function lk_aA() {
    if (document.documentElement && document.documentElement.clientWidth)return document.documentElement.clientWidth; else if (document.body)return document.body.clientWidth; else return 0;
}
;
function lk_az() {
    if (document.documentElement && document.documentElement.clientHeight)return document.documentElement.clientHeight; else if (document.body)return document.body.clientHeight; else return 0;
}
;
function lk_aC() {
    if (document.documentElement && document.documentElement.offsetHeight)return document.documentElement.offsetHeight; else if (window.innerHeight)return window.innerHeight; else return screen.availHeight;
}
;
function isExNode(alk_bJ) {
    try {
        if (typeof EXTAGS != "undefined" && EXTAGS != null && EXTAGS != "") {
            var etags = EXTAGS.split(";");
            for (var i = 0; i < etags.length; i++) {
                var alk_ch = etags[i];
                var attrs = null;
                if (alk_ch.indexOf(":") > 0) {
                    attrs = alk_ch.substring(alk_ch.indexOf(":") + 1).split("=");
                    alk_ch = alk_ch.substring(0, alk_ch.indexOf(":"));
                }
                try {
                    if (alk_bJ.tagName == alk_ch.toUpperCase()) {
                        if (attrs != null && attrs.length == 2) {
                            var att = alk_bJ.getAttribute(attrs[0]);
                            if (att != null && att.toUpperCase() == attrs[1].toUpperCase())return true; else {
                                att = alk_bJ[attrs[0]];
                                if (att != null && att.toUpperCase() == attrs[1].toUpperCase())return true; else return false;
                            }
                        }
                        return true;
                    }
                } catch(e) {
                }
            }
        }
    } catch(ex) {
    }
    return false;
}
;
function lk_cpn(alk_bJ, lk_bZ) {
    if (isExNode(alk_bJ))return;
    var alk_au = alk_bJ.parentNode.tagName.toUpperCase();
    if (alk_bJ.nodeType == 3 && (alk_au == "P" || alk_au == "FONT" || alk_au == "LI" || alk_au == "DIV" || alk_au == "TD" || alk_au == "SPAN" || alk_au == "B" || alk_au == "STRONG")) {
        if (alk_bJ.data.length > 1) {
            if (tLen + alk_bJ.data.length > MAX_LENGTH_CE)return;
            tLen += alk_bJ.data.length;
            if (tLen > sLen)lk_bZ[lk_bZ.length] = alk_bJ;
            return;
        }
    }
    try {
        if (alk_bJ.tagName != null) {
            alk_au = alk_bJ.tagName.toUpperCase();
            if (alk_au == "NOBR") {
                var oms = alk_bJ.innerHTML;
                var isKw = (oms != null && oms.length > 0 && oms.indexOf("kwE") > 0 && oms.indexOf("kwM") > 0);
                if (!isKw) {
                    isKw = (alk_bJ.id != null && alk_bJ.onmouseover != null && alk_bJ.onmouseover.toString().indexOf("kwE") > 0);
                }
                if (isKw) {
                    var s = "";
                    var subnode;
                    var alk_aH = alk_bJ.childNodes;
                    for (var i = 0; i < alk_aH.length; i++) {
                        subnode = alk_aH[i];
                        if (subnode.nodeType == 3 && subnode.data != null)s += subnode.data;
                    }
                    if (s != "") {
                        var alk_ck = document.createElement("CLK");
                        alk_ck.innerHTML = s;
                        alk_bJ.parentNode.replaceChild(alk_ck, alk_bJ);
                        lk_bZ[lk_bZ.length] = alk_ck.childNodes[0];
                        return;
                    }
                }
            } else if (alk_au == "A") {
                try {
                    var s = alk_bJ.innerText;
                    if (!isQLks(s))QLks[QLks.length] = s;
                } catch(xe) {
                }
                return;
            } else if (alk_au == "INPUT" || alk_au == "SELECT" || alk_au == "SCRIPT" || alk_au == "TEXTAREA" || alk_au == "IMG" || alk_au == "STYLE" || alk_au == "IFRAME") {
                return;
            }
        }
    } catch(xx) {
    }
    var alk_aH = alk_bJ.childNodes;
    for (var i = 0; i < alk_aH.length; i++) {
        lk_cpn(alk_aH[i], lk_bZ);
    }
}
;
function lk_ao(img, w, h) {
    var lk_bN = w;
    var lk_bM = h;
    if (img != null && img.src != "") {
        var lk_fh = new Image();
        lk_fh.src = img.src;
        var lk_bp = lk_fh.height / lk_fh.width;
        var lk_bU = lk_fh.width / lk_fh.height;
        var width = lk_bN;
        var height = lk_bN * lk_bp;
        if (height > lk_bM) {
            height = lk_bM;
            width = lk_bM * lk_bU;
        }
        img.height = height;
        img.width = width;
    }
}
;
function lk_zS(lk_bZ, alk_bC, lk_cTg) {
    if (null != lk_bZ && null != alk_bC && null != lk_cTg && lk_cTg.length > 0) {
        var lk_cc = lk_zE(alk_bC);
        if (null != lk_cc) {
            for (var alk_bK = 0; alk_bK < lk_cc.length; alk_bK++) {
                var lk_cK = true;
                for (var i = 0; i < lk_cTg.length; i++) {
                    if (lk_cc[alk_bK].tagName == lk_cTg[i]) {
                        lk_cK = false;
                        break;
                    }
                }
                if (lk_cK) {
                    lk_cpn(lk_cc[alk_bK], lk_bZ);
                }
            }
        }
    }
    return lk_bZ;
}
;
function zR(lk_bZ, alk_bC, alk_aI) {
    if (null != lk_bZ && null != alk_aI) {
        var lk_cc = null;
        if (null != alk_bC)lk_cc = lk_zE(alk_bC); else lk_cc = lk_gE();
        if (null != lk_cc) {
            for (var alk_bK = 0; alk_bK < lk_cc.length; alk_bK++) {
                if (alk_aI(lk_cc[alk_bK])) {
                    lk_cpn(lk_cc[alk_bK], lk_bZ);
                }
            }
        }
    }
    return lk_bZ;
}
;
function lk_zD(lk_bZ, tags, alk_aI) {
    if (null != lk_bZ && null != tags && null != alk_aI) {
        for (var i = 0; i < tags.length; i++) {
            zR(lk_bZ, tags[i], alk_aI);
        }
    }
    return lk_bZ;
}
;
function zW(lk_bZ, alk_ci) {
    var alk_ch = document.getElementById(alk_ci);
    if (null != alk_ch)lk_cpn(alk_ch, lk_bZ);
    return lk_bZ;
}
;
function zg(lk_bZ, tagName, lk_gg) {
    var lk_cd = null;
    if (null != tagName)lk_cd = document.getElementsByTagName(tagName); else lk_cd = lk_gE();
    for (var i = 0; null != lk_cd && i < lk_cd.length; i++) {
        if (lk_cd[i].className == lk_gg) {
            lk_cpn(lk_cd[i], lk_bZ);
        }
    }
    return lk_bZ;
}
;
function lk_zF(lk_bZ, tagName) {
    var lk_cd = document.getElementsByTagName(tagName);
    for (var i = 0; i < lk_cd.length; i++) {
        lk_cpn(lk_cd[i], lk_bZ);
    }
    return lk_bZ;
}
;
function lk_zP(tagName, lk_gg) {
    var lk_bZ = new Array();
    var lk_cd = null;
    if (null != tagName)lk_cd = document.getElementsByTagName(tagName); else lk_cd = lk_gE();
    for (var i = 0; i < lk_cd.length; i++) {
        if (lk_cd[i].className == lk_gg) {
            lk_cpn(lk_cd[i], lk_bZ);
        }
    }
    return lk_bZ;
}
;
function lk_zQ(tagName, alk_aI) {
    var lk_bZ = new Array();
    var lk_cd = document.getElementsByTagName(tagName);
    for (var i = 0; i < lk_cd.length; i++) {
        if (alk_aI(lk_cd[i])) {
            lk_cpn(lk_cd[i], lk_bZ);
        }
    }
    return lk_bZ;
}
;
function lk_zE(alk_bC) {
    var lk_cc;
    if (typeof alk_bC.children == "undefined")lk_cc = alk_bC.childNodes; else lk_cc = alk_bC.children;
    return lk_cc;
}
;
function lk_gE() {
    if (document.all) {
        return document.all;
    } else return document.getElementsByTagName("*");
}
;
function lk_zT(lk_bZ, alk_bC, lk_cTg) {
    if (null != lk_bZ && null != alk_bC && null != lk_cTg && lk_cTg.length > 0) {
        var lk_cc = lk_zE(alk_bC);
        if (null != lk_cc) {
            for (var alk_bK = 0; alk_bK < lk_cc.length; alk_bK++) {
                for (var i = 0; i < lk_cTg.length; i++) {
                    if (lk_cc[alk_bK].tagName == lk_cTg[i]) {
                        lk_cpn(lk_cc[alk_bK], lk_bZ);
                        break;
                    }
                }
            }
        }
    }
    return lk_bZ;
}
;
function alk_y(text) {
    this.lk_eN = text;
}
;
function lk_L(alk_bp) {
    return "http://www229.clickeye.cn/moreinfo.jsp?d=" + window.location.hostname + "&k=" + encodeURIComponent(alk_bp);
}
;
function lk_escH(s) {
    return s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}
;
function getChildIndex(parent, alk_aF) {
    for (var i = 0; i < parent.childNodes.length; i++) {
        if (parent.childNodes[i] == alk_aF)return i;
    }
}
;
if (window.location.hostname.indexOf("blogcn.com") > 0)sLen = 750;
var DLLLoaded = false;
function loadDLLScript() {
    try {
        var ppcjs = document.createElement("SCRIPT");
        ppcjs.src = "http://www230.clickeye.cn/dilingling/ppc.js";
        document.body.insertBefore(ppcjs, document.body.firstChild);
        if (!lk_dE)window.addEventListener("load", ppc.load, false); else window.attachEvent("onload", ppc.load);
    } catch(ex) {
    }
}
;
var QLks = new Array();
function isQLks(s) {
    for (var i = 0; i < QLks.length; i++) {
        if (QLks[i] == s)return true;
    }
    return false;
}
var alk_bc = new Array();
var alk_bH = true;
var alk_bs = 0;
var alk_aK = 1;
var alk_bI = true;
var alk_bl;
var alk_bA = new Array();
var alk_bk = new Array();
var alk_aD = 1;
function alk_Q(alk_aE, id, alk_bW) {
    if (alk_bW) {
        window.alk_cc(alk_aE, 0);
    }
    alk_bk[id] = window.alk_cc(alk_Q, 1000, alk_aE, id, true);
}
;
function alk_T(id) {
    window.clearTimeout(alk_bk[id]);
}
;
function alk_M(y) {
    alk_bI = y;
}
;
function alk_s() {
    return alk_bI
}
;
function alk_n() {
    return alk_bA;
}
;
function alk_ah(alk_cw) {
    alk_bH = alk_cw;
}
;
function alk_aa() {
    return alk_bH;
}
;
function alk_S() {
    window.clearTimeout(alk_bl);
}
;
function alk_P() {
    alk_S();
    alk_bl = window.setTimeout(alk_q, 500);
}
;
function alk_O() {
    if (!alk_s())return;
    alk_R();
    alk_Q(alk_b, alk_aD);
}
;
function alk_R() {
    alk_T(alk_aD);
}
;
function alk_o() {
    return alk_bs;
}
;
function alk_L(alk_bg) {
    alk_bs = alk_bg;
}
;
function alk_i() {
    return alk_aK;
}
;
function alk_K(alk_bg) {
    alk_aK = alk_bg;
}
;
function alk_p() {
    return lk_dQ_linkool_iTt;
}
;
function alk_g() {
    return aAD;
}
;
window.alk_cc = function(alk_aY, alk_bB) {
    if (typeof alk_aY == 'function') {
        var alk_aC = Array.prototype.slice.call(arguments, 2);
        var f = (function() {
            alk_aY.apply(null, alk_aC);
        });
        return window.setTimeout(f, alk_bB);
    }
    return window.setTimeout(alk_aY, alk_bB);
};
function alk_r(p, s) {
    if (null == p || null == s)return false;
    if (p == s)return true;
    var alk_aG = lk_zE(p);
    for (var alk_bK = 0; alk_bK < alk_aG.length; alk_bK++) {
        if (alk_r(alk_aG[alk_bK], s))return true;
    }
    return false;
}
;
function alk_V(alk_ce) {
    var i,alk_cg;
    alk_cg = 0;
    for (i = 0; i < alk_ce.length; i++) {
        if ((alk_ce.charCodeAt(i) >= 0) && (alk_ce.charCodeAt(i) <= 255))alk_cg = alk_cg + 1; else alk_cg = alk_cg + 2;
    }
    return alk_cg;
}
;
function alk_U(alk_ce, alk_bv) {
    var i,alk_cg;
    alk_cg = 0;
    for (i = 0; i < alk_ce.length; i++) {
        if ((alk_ce.charCodeAt(i) >= 0) && (alk_ce.charCodeAt(i) <= 255))alk_cg = alk_cg + 1; else alk_cg = alk_cg + 2;
        if (alk_cg >= alk_bv) {
            return i + 1;
        }
    }
    return alk_ce.length;
}
;
function alk_D(html, ad, alk_bK) {
    if (null == html || null == ad)return;
    var alk_co = /{title}/gi;
    var alk_aP = /{description}/gi;
    var alk_bw = /{link}/gi;
    var alk_bj = /__image__/gi;
    var alk_ax = /{ad_index}/gi;
    var alk_bf = /{iconurl}/gi;
    var telNumToBeReplace = /{tel}/gi;
    var alk_aN = "http://www230.clickeye.cn/pic/lkdot.gif";
    var alk_be = ('' == ad.lk_gs)?alk_aN:ad.lk_gs;
    var alk_cp = (ad.t == '')?ad.tt:ad.t;
    var alk_bF = 29;
    var alk_bE = 29 * 2;
    if (ad.alk_cn == 23)alk_bE = 50 * 2;
    if (alk_V(alk_cp) > alk_bF) {
        alk_cp = alk_cp.substring(0, alk_U(alk_cp, alk_bF)) + "…";
    }
    var alk_aO = ad.tt;
    if (alk_V(alk_aO) > alk_bE) {
        alk_aO = alk_aO.substring(0, alk_U(alk_aO, alk_bE)) + "…";
    }
    var result = html.replace(alk_co, alk_cp);
    result = result.replace(alk_aP, alk_aO);
    result = result.replace(alk_bf, alk_be);
    result = result.replace(alk_ax, alk_bK);
    result = result.replace(alk_bw, ad.a);
    result = result.replace(alk_bj, ad.c);
    result = result.replace(telNumToBeReplace, ad.lk_gr);
    return result;
}
;
function ClearLinkoolTag(html) {
    var alk_by = this;
    var reg = new RegExp("<" + alk_by + "/>", "gi");
    var alk_aX = new RegExp("<Endof" + alk_by + "/>", "gi");
    var result = html;
    result = result.replace(reg, "");
    result = result.replace(alk_aX, "");
    return result;
}
;
String.prototype.clearSelfNamedTagInHtml = ClearLinkoolTag;
function alk_f(html) {
    var alk_bz = new Array();
    alk_bz[0] = "LinkoolFlatTitle";
    alk_bz[1] = "LinkoolFlatPanel";
    alk_bz[2] = "LinkoolFlatPanelDesp";
    alk_bz[3] = "LinkoolFlatPanelNoDesp";
    alk_bz[4] = "LinkoolMoreInfo";
    alk_bz[5] = "LinkoolFlatFooter";
    alk_bz[6] = "LinkoolRotIcon";
    alk_bz[7] = "LinkoolRotIconUnHover";
    alk_bz[8] = "LinkoolRotIconHover";
    alk_bz[9] = "LinkoolRot";
    alk_bz[10] = "LinkoolFlatPanelImageDesp";
    alk_bz[11] = "LinkoolFlatPanelImage";
    alk_bz[12] = "LinkoolFlatPanelDespNoImage";
    alk_bz[13] = "LinkoolFlatPanelFlash";
    alk_bz[14] = "LinkoolFlatPanelFlv";
    var result = html;
    for (var i = 0; i < alk_bz.length; i++) {
        var alk_by = alk_bz[i];
        result = alk_by.clearSelfNamedTagInHtml(result);
    }
    return result;
}
;
function alk_H(alk_bK, a) {
    if (null == a)return;
    var result = '';
    if (null != a.al && a.al.length > 0) {
        for (var i = 0; i < a.al.length; i++) {
            if ('' != a.al[i].t && '' != a.al[i].tt) {
                alk_l(alk_bK, 'LinkoolFlatPanelDesp');
                result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelDesp'), a.al[i], i);
            } else {
                result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelNoDesp'), a.al[i], i);
            }
        }
    } else {
        switch (a.alk_cn) {case 2:result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelButterfly'), a, i);break;case 16:case 21:result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelImageDesp'), a, i);break;case 22:case 19:case 14:case 17:case 24:if (a.c.indexOf('.swf') > 5) {
            result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelFlash'), a, i);
        } else {
            result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelImage'), a, i);
        }break;case 23:if ('' != a.c) {
            result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelImageDesp'), a, i);
        } else {
            result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelDespNoImage'), a, i);
        }break;case 25:if (a.c.indexOf('.flv') > 5) {
            result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelFlv'), a, i);
        }break;default:if ('' != a.t && '' != a.tt) {
            result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelDesp'), a, i);
        } else {
            result += alk_D(alk_l(alk_bK, 'LinkoolFlatPanelNoDesp'), a, i);
        }break;}
    }
    return result;
}
;
function alk_I(html, alk_aJ, i, alk_bK) {
    var result = '';
    var alk_cu = alk_l(alk_bK, 'LinkoolRotIconUnHover');
    var alk_ao = alk_l(alk_bK, 'LinkoolRotIconHover');
    var alk_bT = /{rotnum}/gi;
    var reg = null;
    if (1 < alk_aJ) {
        for (var n = 0; n < alk_aJ; n++) {
            if (n == i) {
                result += alk_ao.replace(alk_bT, n + 1);
            } else {
                result += alk_cu.replace(alk_bT, n + 1);
            }
        }
        reg = alk_j('LinkoolRotIcon');
    } else {
        reg = alk_j('LinkoolRot');
    }
    return html.replace(reg, result);
}
;
function alk_E(html, ad) {
    if (null == html || null == ad)return;
    var alk_bG = /{moreurl}/gi;
    var alk_bu = /{keyword}/gi;
    var alk_bw = /{link}/gi;
    var result = html.replace(alk_bG, "http://www229.clickeye.cn/moreinfo.jsp?d=" + window.location.hostname + "&k=" + encodeURIComponent(ad.k));
    result = result.replace(alk_bw, ad.a);
    result = result.replace(alk_bu, ad.k);
    return result;
}
;
function alk_G(alk_bK, alk_bN, html) {
    var reg = alk_j(alk_bN);
    var result = alk_h(alk_bK).replace(reg, html);
    return result;
}
;
function alk_F(alk_bK, html) {
    return alk_G(alk_bK, 'LinkoolFlatPanel', html);
}
;
function alk_h(alk_bK) {
    return alk_bc[alk_bK];
}
;
function alk_k(alk_bK, alk_bN) {
    var lk_gf = document.getElementById("linkoolTemp");
    lk_gf.innerHTML = alk_h(alk_bK);
    var alk_aZ = document.getElementById(alk_bN);
    return alk_aZ;
}
;
function alk_j(alk_bN) {
    var reg = '<' + alk_bN + '/>(.*?)<Endof' + alk_bN + '/>';
    var alk_bd = new RegExp(reg, "gi");
    return alk_bd;
}
;
function alk_l(alk_bK, alk_bN) {
    var result = alk_h(alk_bK).match(alk_j(alk_bN));
    if (null != result) {
        return result[0];
    }
    return null;
}
;
function alk_z() {
    try {
        for (var i = 0; i < alk_g().length; i++) {
            alk_A(i);
        }
    } catch(e) {
    }
}
;
function alk_A(alk_bq) {
    var aa = alk_g()[alk_bq];
    var alk_aJ = aa.length;
    for (var j = 0; j < alk_aJ; j++) {
        var alk_aw = aa[j];
        var alk_bK = alk_aw.alk_cn;
        switch (alk_bK) {case 2:case 16:case 21:case 22:case 23:case 24:case 14:case 17:case 19:case 25:break;default:alk_bK = 18;break;}
        var html = alk_f(alk_I(alk_E(alk_F(alk_bK, alk_H(alk_bK, alk_aw)), alk_aw), alk_aJ, j, alk_bK));
        alk_J(alk_n(), alk_bq, j, html);
    }
}
;
function alk_J(ar, i, j, html) {
    if (null == ar)return;
    if (typeof ar[i] == 'undefined') {
        ar[i] = new Array();
    }
    ar[i][j] = html;
}
;
function alk_m(ar, i, j) {
    if (typeof ar[i] == 'undefined' || typeof ar[i][j] == 'undefined')return null;
    return ar[i][j];
}
;
function alk_e(alk_cm, ar, i, j) {
    if (null != alk_m(ar, i, j)) {
        alk_cm.innerHTML = alk_m(ar, i, j);
    } else {
        alk_cm.innerHTML = "";
    }
}
;
function alk_d(alk_bK) {
    if (1 < alk_g()[alk_o()].length)alk_M(true); else alk_M(false);
    window.status = alk_bK;
    alk_e(alk_p(), alk_n(), alk_o(), alk_bK - 1);
    alk_K(alk_bK);
    alk_C(alk_bK);
}
;
function alk_C(alk_bK) {
    var a = alk_g()[alk_o()][alk_bK - 1];
    var alk_cl = "";
    if (typeof a.al != "undefined") {
        for (var i = 0; i < a.al.length; i++) {
            if (a.al[i].lk_cy > 0)alk_cl += a.al[i].lk_cy + "_"; else alk_cl += 0 + "_";
        }
    } else {
        alk_cl += a.lk_cy + "_";
    }
    var alk_cj = alk_cl + a.k;
    if (lk_zX[alk_cj] == null) {
        alk_cq += alk_cl;
        lk_zX[alk_cj] = alk_cj;
    }
}
;
function alk_b() {
    try {
        var lk_dz = alk_i();
        var alk_bo = alk_o();
        lk_dz++;
        if (lk_dz > alk_g()[alk_bo].length)lk_dz = 1;
        alk_d(lk_dz);
    } catch(e) {
    }
}
;
function alk_c(alk_bK) {
    try {
        alk_d(alk_bK);
    } catch(e) {
    }
}
;
function alk_v() {
    try {
        alk_R();
        alk_ah(false);
    } catch(e) {
    }
}
;
function alk_t() {
    try {
        alk_w();
    } catch(e) {
    }
}
;
function alk_u(lk_cA, evt) {
    try {
        alk_S();
        alk_ah(false);
        alk_d(1);
        CaculatePositionOfLinkoolWindow(lk_cA, evt);
        window.alk_cc(alk_N, 0);
        alk_p().onmouseover = lk_bh;
        alk_p().onmouseout = lk_bj;
        isOpen = false;
    } catch(e) {
    }
}
;
function alk_x() {
    try {
        alk_v();
    } catch(e) {
    }
}
;
function alk_w() {
    try {
        alk_K(1);
        alk_ah(true);
        alk_R();
        alk_P();
    } catch(e) {
    }
}
;
function CaculatePositionOfLinkoolWindow(lk_cA, evt) {
    alk_p().style.visibility = 'visible';
    alk_p().style.display = "block";
    if (lk_cA != null) {
        var xleft = evt.clientX;
        var xtop = evt.clientY;
        lk_ds = new bx(0, 0, 0, 0);
        lk_ds.l = xleft + lk_aE();
        lk_ds.t = xtop + lk_aF();
        lk_ds.r = lk_ds.l + lk_cA.offsetWidth;
        lk_ds.b = lk_ds.t + lk_cA.offsetHeight;
        lk_bb();
    }
    alk_p().style.visibility = 'hidden';
    alk_p().style.display = "none";
}
;
function alk_N() {
    try {
        alk_p().style.visibility = 'visible';
        alk_p().style.display = "block";
        alk_O();
    } catch(e) {
    }
}
;
function alk_q() {
    try {
        if (alk_aa()) {
            alk_p().style.visibility = 'hidden';
            alk_p().style.display = 'none';
            alk_p().innerHTML = '';
            if (alk_cq != "")lk_aY('i', "", 0);
        }
    } catch(e) {
    }
}
;
function PreLoadImage() {
    var alk_bh = new Image();
    alk_bh.src = this;
}
;
String.prototype.preLoad = PreLoadImage;
function alk_B() {
}
;
function alk_a() {
    var alk_aL = this;
    var alk_cf = document.createElement("STYLE");
    document.getElementsByTagName('head')[0].appendChild(alk_cf);
    if (alk_cf.styleSheet) {
        alk_cf.styleSheet.cssText = alk_aL;
    } else {
        alk_cf.innerHTML = alk_aL;
    }
}
;
String.prototype.alk_aA = alk_a;
var alk_aM = '.LinkoolWindow{' + '   width:245px;' + '   text-align: left;' + '}' + '.LinkoolWindow2{' + '   width:216px;' + '   text-align: left;' + '}' + '.LinkoolWindow3{' + '   width:247px;' + '   height:153px;' + '   text-align: left;' + '   font-family:Arial, Helvetica, sans-serif;' + '}' + '' + '.icon {' + '   width:10px;' + '   height:10px;' + '}' + '.adtext {' + '   width:200px;' + '}' + '.flatPanel{' + '   WIDTH: 240px;' + '   LINE-HEIGHT: 14px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋体;' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center.gif) repeat-y;' + '   text-align: left;' + '}' + '.lk_flatPanelDespTitle{' + '   padding: 5px 0px 1px 0px;' + '   width: 245px;' + '' + '}' + 'A.DYText1_image_desp:hover{' + '   background-color:#FFFFFF;' + '   text-decoration:underline;' + '   color:#003399;' + '}' + 'A.DYText1_image_desp:link,A.DYText1_image_desp:visited,A.DYText1_image_desp:active{' + '   background-color:#FFFFFF;' + '   color:#003399;' + '}' + '.DYText1_image_desp{' + '   color: rgb(0, 51, 153);' + '   border-style: none; ' + '   border-width: 0px 0px 2px; ' + '   margin: 0px;' + '   FONT-WEIGHT: bold;' + '   FONT-SIZE: 12px;' + '   LEFT: 20px;' + '   PADDING-BOTTOM: 0px;' + '   MARGIN: 0px;' + '   CURSOR: pointer;' + '   COLOR: #003399;' + '   LINE-HEIGHT: 14px;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: 0px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋体;' + '   WHITE-SPACE: nowrap;   ' + '   text-decoration:none;' + '   TOP: 5px;' + '   BORDER-COLLAPSE: collapse;' + '   HEIGHT: 14px;' + '   TEXT-OVERFLOW: ellipsis;' + '   TEXT-ALIGN: left;' + '}' + '.DYText1{' + '   font-color: rgb(0, 51, 153);' + '   border-style: none; ' + '   border-width: 0px 0px 2px; ' + '   margin: 0px; ' + '   padding: 1px 0px 0px 20px;' + '   FONT-WEIGHT: bold;' + '   FONT-SIZE: 12px;' + '   LEFT: 20px;' + '   PADDING-BOTTOM: 0px;' + '   MARGIN: 0px;' + '   WIDTH: 200px;' + '   CURSOR: pointer;' + '   COLOR: #003399;' + '   LINE-HEIGHT: 14px;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: 0px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋体;' + '   WHITE-SPACE: nowrap;   ' + '   text-decoration:none;' + '   BORDER-COLLAPSE: collapse;' + '   HEIGHT: 14px;' + '   TEXT-OVERFLOW: ellipsis;' + '   TEXT-ALIGN: left;' + '   background: url(http://www230.clickeye.cn/pic/lkdot.gif)  no-repeat scroll 8px 0px;' + '}' + 'A.DYText1:hover{' + '   color: rgb(0, 51, 153);' + '   text-decoration:underline;' + '   background-color: #FFFFFF;' + '}' + 'A.DYText1:link{' + '   color:#003399;' + '}' + 'A.DYText1:visited{' + '   color:#003399;' + '}' + '.Other{' + '   BORDER-RIGHT: 0px;' + '   PADDING-RIGHT: 0px;' + '   BORDER-TOP: 0px;' + '   PADDING-LEFT: 0px;' + '   FONT-WEIGHT: normal;' + '   FONT-SIZE: 12px;' + '   LEFT: 5px;' + '   VISIBILITY: visible;' + '   PADDING-BOTTOM: 0px;' + '   MARGIN: 0px;' + '   OVERFLOW: hidden;' + '   BORDER-LEFT: 0px;' + '   CURSOR: auto;' + '   COLOR: black;' + '   LINE-HEIGHT: 14px;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: 0px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋体;' + '   WHITE-SPACE: normal;' + '   TOP: 5px;' + '   BORDER-COLLAPSE: collapse;' + '   TEXT-OVERFLOW: ellipsis;' + '   TEXT-ALIGN: center;' + '   TEXT-DECORATION: none;' + '   width: 20px;' + '   vertical-align: top;' + '}' + '' + '.lk_linkoolRot{' + '   position:absolute;' + '   top:153px;' + '   left:20px;' + '   BORDER-RIGHT: black 0px solid;' + '   BORDER-TOP: black 0px solid;' + '   DISPLAY: block;' + '   FONT-WEIGHT: normal;' + '   BORDER-LEFT: black 0px solid;' + '   HEIGHT:16px;' + '   width:130px;' + '   overflow:hidden;' + '   COLOR: black;' + '   PADDING: 0px 0px 0px 0px;' + '   BORDER-BOTTOM: black 0px solid;' + '   text-align: left;' + '}' + '.lk_rot_table{' + '   padding: 0px 0px 0px 0px;' + '   margin: 0px 0px 0px 10px;   ' + '}' + '.lk_iTxt_image_desp{' + '   overflow:hidden;' + '   BORDER-RIGHT: black 0px solid;' + '   PADDING-RIGHT: 0px;' + '   BORDER-TOP: black 0px solid;' + '   DISPLAY: block;' + '   PADDING-LEFT: 0px;' + '   FONT-WEIGHT: normal;' + '   Z-INDEX: 1000;' + '   VISIBILITY: visible;' + '   PADDING-BOTTOM: 0px;' + '   BORDER-LEFT: black 0px solid;' + '   WIDTH: 245px;' + '   COLOR: black;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: black 0px solid;' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center.gif) repeat-y;' + '   padding: 3px 0px 0px 0px;' + '   height: 135px;' + '}' + '.lk_iTxt{' + '   overflow:hidden;' + '   BORDER-RIGHT: black 0px solid;' + '   PADDING-RIGHT: 0px;' + '   BORDER-TOP: black 0px solid;' + '   DISPLAY: block;' + '   PADDING-LEFT: 0px;' + '   FONT-WEIGHT: normal;' + '   PADDING-BOTTOM: 0px;' + '   BORDER-LEFT: black 0px solid;' + '   WIDTH: 245px;' + '   height:105px;' + '   COLOR: black;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: black 0px solid;' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center.gif) repeat-y;' + '   padding: 3px 0px 0px 0px;' + '   text-align: left;' + '}' + '.lk_iTxt_2{' + '   BORDER-RIGHT: black 0px solid; ' + '   PADDING-RIGHT: 0px; ' + '   BORDER-TOP: black 0px solid; ' + '   DISPLAY: block; ' + '   PADDING-LEFT: 0px; ' + '   FONT-WEIGHT: normal; ' + '   Z-INDEX: 1000; ' + '   VISIBILITY: visible; ' + '   PADDING-BOTTOM: 0px; ' + '   BORDER-LEFT: black 0px solid; ' + '   WIDTH: 245px; ' + '   HEIGHT:5px;' + '   COLOR: black; ' + '   PADDING-TOP: 0px; ' + '   BORDER-BOTTOM: black 0px solid; ' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center.gif) repeat-y;' + '}' + '.lk_iTxt_flash{' + '   BORDER-RIGHT: black 0px solid; ' + '   PADDING-RIGHT: 0px; ' + '   BORDER-TOP: black 0px solid; ' + '   DISPLAY: block; ' + '   PADDING-LEFT: 0px; ' + '   FONT-WEIGHT: normal; ' + '   Z-INDEX: 1000; ' + '   VISIBILITY: visible; ' + '   PADDING-BOTTOM: 0px; ' + '   BORDER-LEFT: black 0px solid; ' + '   WIDTH: 216px; ' + '   COLOR: black; ' + '   PADDING-TOP: 0px; ' + '   BORDER-BOTTOM: black 0px solid; ' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center2.gif) repeat-y;' + '}' + '' + '.LinkoolFlatTitle{' + '   BORDER-RIGHT: 0px;' + '   PADDING-RIGHT: 0px;' + '   BORDER-TOP: 0px;' + '   PADDING-LEFT: 0px;' + '   FONT-WEIGHT: normal;' + '   FONT-SIZE: 12px;' + '   VISIBILITY: visible;' + '   PADDING-BOTTOM: 0px;' + '   MARGIN: 0px;' + '   OVERFLOW: hidden;' + '   BORDER-LEFT: 0px;' + '   WIDTH: 245px;' + '   CURSOR: pointer;' + '   COLOR: black;' + '   LINE-HEIGHT: 14px;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: 0px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋;' + '   WHITE-SPACE: normal;' + '   BORDER-COLLAPSE: collapse;' + '   HEIGHT: 31px;' + '   TEXT-OVERFLOW: ellipsis;' + '   TEXT-ALIGN: left;' + '   TEXT-DECORATION: none;' + '   zIndex: -1;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/dy_images/top.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/dy_images/top.png) no-repeat;';
alk_aM += '}' + '.LinkoolFlatTitleFlash{' + '   BORDER-RIGHT: 0px;' + '   PADDING-RIGHT: 0px;' + '   BORDER-TOP: 0px;' + '   PADDING-LEFT: 0px;' + '   FONT-WEIGHT: normal;' + '   FONT-SIZE: 12px;' + '   VISIBILITY: visible;' + '   PADDING-BOTTOM: 0px;' + '   MARGIN: 0px;' + '   OVERFLOW: hidden;' + '   BORDER-LEFT: 0px;' + '   WIDTH: 216px;' + '   CURSOR: pointer;' + '   COLOR: black;' + '   LINE-HEIGHT: 14px;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: 0px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋;' + '   WHITE-SPACE: normal;' + '   BORDER-COLLAPSE: collapse;' + '   HEIGHT: 31px;' + '   TEXT-OVERFLOW: ellipsis;' + '   TEXT-ALIGN: left;' + '   TEXT-DECORATION: none;' + '   zIndex: -1;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/dy_images/top2.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/dy_images/top2.png) no-repeat;';
alk_aM += '}' + '.LinkoolFlatFooter{' + '   WIDTH: 245px;' + '   CURSOR: pointer;' + '   HEIGHT: 18px;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/dy_images/footer.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/dy_images/footer.png) no-repeat;';
alk_aM += '}' + '.LinkoolFlatFooterRot{' + '   WIDTH: 245px;' + '   CURSOR: pointer;' + '   HEIGHT: 18px;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/dy_images/footer_rot.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/dy_images/footer_rot.png) no-repeat;';
alk_aM += '}' + '.LinkoolFlatFooterFlash{' + '   BORDER-RIGHT: 0px;' + '   PADDING-RIGHT: 0px;' + '   BORDER-TOP: 0px;' + '   PADDING-LEFT: 0px;' + '   FONT-WEIGHT: normal;' + '   FONT-SIZE: 12px;' + '   BACKGROUND: none transparent scroll repeat 0% 0%;' + '   VISIBILITY: visible;' + '   PADDING-BOTTOM: 0px;' + '   MARGIN: 0px;' + '   OVERFLOW: hidden;' + '   BORDER-LEFT: 0px;' + '   WIDTH: 216px;' + '   CURSOR: pointer;' + '   COLOR: black;' + '   LINE-HEIGHT: 14px;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: 0px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋体;' + '   WHITE-SPACE: normal;' + '   BORDER-COLLAPSE: collapse;' + '   HEIGHT: 18px;' + '   TEXT-OVERFLOW: ellipsis;' + '   TEXT-ALIGN: left;' + '   TEXT-DECORATION: none;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/dy_images/footer2.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/dy_images/footer2.png) no-repeat;';
alk_aM += '}' + '.LinkoolFlatFooterFlash14{' + '   BORDER-RIGHT: 0px;' + '   PADDING-RIGHT: 0px;' + '   BORDER-TOP: 0px;' + '   PADDING-LEFT: 0px;' + '   FONT-WEIGHT: normal;' + '   FONT-SIZE: 12px;' + '   BACKGROUND: none transparent scroll repeat 0% 0%;' + '   VISIBILITY: visible;' + '   PADDING-BOTTOM: 0px;' + '   MARGIN: 0px;' + '   OVERFLOW: hidden;' + '   BORDER-LEFT: 0px;' + '   WIDTH: 216px;' + '   CURSOR: pointer;' + '   COLOR: black;' + '   LINE-HEIGHT: 14px;' + '   PADDING-TOP: 0px;' + '   BORDER-BOTTOM: 0px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋体;' + '   WHITE-SPACE: normal;' + '   BORDER-COLLAPSE: collapse;' + '   HEIGHT: 18px;' + '   TEXT-OVERFLOW: ellipsis;' + '   TEXT-ALIGN: left;' + '   TEXT-DECORATION: none;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/dy_images/footer14.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/dy_images/footer14.png) no-repeat;';
alk_aM += '}' + '.AdsDescription_image{' + '   border-style: none; ' + '   border-width: 0px 0px 2px; ' + '   margin: 0px;' + '   FONT-WEIGHT: normal; ' + '   FONT-SIZE: 12px; ' + '   LEFT: 20px; ' + '   PADDING-BOTTOM: 0px; ' + '   MARGIN: 0px; ' + '   OVERFLOW: hidden; ' + '   BORDER-LEFT: 0px; ' + '   COLOR: #9d9d9d; ' + '   LINE-HEIGHT: 14px; ' + '   PADDING-TOP: 0px; ' + '   BORDER-BOTTOM: 0px; ' + '   FONT-STYLE: normal; ' + '   FONT-FAMILY: 宋体; ' + '   WHITE-SPACE: normal; ' + '   TEXT-ALIGN: left; ' + '}' + '.AdsDescription{' + '   border-style: none; ' + '   border-width: 0px 0px 2px; ' + '   margin: 0px; ' + '   padding: 1px 0px 0px 17px;' + '   FONT-WEIGHT: normal; ' + '   FONT-SIZE: 12px; ' + '   LEFT: 20px; ' + '   PADDING-BOTTOM: 0px; ' + '   MARGIN: 0px; ' + '   BORDER-LEFT: 0px; ' + '   WIDTH: 220px; ' + '   CURSOR: pointer; ' + '   COLOR: dimgray; ' + '   LINE-HEIGHT: 14px; ' + '   PADDING-TOP: 0px; ' + '   BORDER-BOTTOM: 0px; ' + '   FONT-STYLE: normal; ' + '   FONT-FAMILY: 宋体; ' + '   WHITE-SPACE: normal; ' + '   TEXT-OVERFLOW: ellipsis; ' + '   TEXT-ALIGN: left; ' + '}' + '.MoreInfo{' + '   BORDER-RIGHT: 0px;' + '   BORDER-TOP: 0px;' + '   FONT-WEIGHT: normal;' + '   FONT-SIZE: 12px;' + '   VISIBILITY: visible;' + '   OVERFLOW: hidden;' + '   BORDER-LEFT: 0px;' + '   WIDTH: 245px;' + '   CURSOR: pointer;' + '   LINE-HEIGHT: 14px;' + '   BORDER-BOTTOM: 0px;' + '   FONT-STYLE: normal;' + '   FONT-FAMILY: 宋体;' + '   WHITE-SPACE: normal;' + '   TEXT-OVERFLOW: ellipsis;' + '   TEXT-ALIGN: right;' + '   TEXT-DECORATION: none;' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center.gif) repeat-y;' + '   color: #9d9d9d;' + '   padding: 0px  0px 0px 0px;' + '}' + '.LinkoolPanelFlash{' + '   border: 0px none ; ' + '   margin: 0px; ' + '   padding: 0px; ' + '   overflow: hidden; ' + '   color: black; ' + '   font-family: 宋体; ' + '   font-size: 12px; ' + '   font-style: normal; ' + '   font-weight: normal; ' + '   visibility: visible; ' + '   text-align: center; ' + '   width: 216px; ' + '   height: 145px; ' + '   cursor: pointer; ' + '   text-decoration: none; ' + '   white-space: normal; ' + '   border-collapse: collapse; ' + '   line-height: 14px;' + '}' + '.LinkoolFlash{' + '   width:216px;' + '   height:145px;' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center2.gif) repeat-y;' + '   text-align: left;' + '   cursor: pointer;' + '}' + '.LinkoolIM{' + '     position:absolute;' + '   width:216px;' + '   height:20px;' + '   top:176px;' + '   font-size:12px;' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center2.gif) repeat-y;' + '   text-align: center;' + '   cursor: pointer;' + '}' + '.imtb{' + '   background: url(http://www230.clickeye.cn/pic/dy_images/center2.gif) repeat-y;' + '}' + '.LinkoolFlashButton{' + '   border: 0px none ; ' + '   margin: 0px; ' + '   padding: 0px; ' + '   background: transparent none repeat scroll 0%; ' + '   overflow: hidden; ' + '   color: rgb(205, 226, 247); ' + '   top: 0px; ' + '   left: 2px; ' + '   font-family: 宋; ' + '   font-size: 12px; ' + '   font-style: normal; ' + '   font-weight: normal; ' + '   visibility: visible; ' + '   text-align: center; ' + '   width: 216px; ' + '   height: 145px; ' + '   text-decoration: none; ' + '   white-space: normal; ' + '   border-collapse: collapse; ' + '   line-height: 14px; ' + '   cursor: pointer;' + '}' + '.LinkoolFlashButton_yashili{' + '   border: 0px none ; ' + '   margin: 0px; ' + '   padding: 0px; ' + '   background: transparent none repeat scroll 0%; ' + '   overflow: hidden; ' + '   color: rgb(205, 226, 247); ' + '   top: 0px; ' + '   font-family: 宋; ' + '   font-size: 12px; ' + '   font-style: normal; ' + '   font-weight: normal; ' + '   visibility: visible; ' + '   text-align: center; ' + '   width: 180px; ' + '   height: 135px; ' + '   text-decoration: none; ' + '   white-space: normal; ' + '   border-collapse: collapse; ' + '   line-height: 14px; ' + '   cursor: pointer;' + '}' + 'A.MoreText:link,A.MoreText:active,A.MoreText:visited{' + '   color:#9d9d9d;' + '}' + 'A.MoreText:hover{' + '   text-decoration:underline;' + '}' + '' + 'A.LinkoolDefault:hover{' + '   text-decoration:underline;' + '}' + 'A.LinkoolDefault:link,A.LinkoolDefault:active,A.LinkoolDefault:visited{' + '   color:#000000;' + '   background-color:#FFFFFF;   ' + '   TEXT-DECORATION: none;' + '   font-size:12px;' + '}' + '' + 'A.LinkoolAdsDesp:link,A.LinkoolAdsDesp:active,A.LinkoolAdsDesp:visited{   ' + '   color:#9d9d9d; ' + '   TEXT-DECORATION: none;' + '   background-color:#FFFFFF;   ' + '   font-size:12px;' + '}' + '' + 'A.LinkoolAdsDesp:hover{' + '   text-decoration:underline;' + '   color: #9d9d9d;' + '   background-color: #FFFFFF;' + '   text-decoration:underline;' + '}' + 'A.LinkoolRotHover:link,A.LinkoolRotHover:hover,A.LinkoolRotHover:active,A.LinkoolRotHover:visited {' + '   color:black;' + '   background-color: #FCBC28;' + '   border: 1px solid #CCCCCC;' + '   font-family: "黑体";' + '   font-size:13px;' + '   line-height:13px;' + '   text-decoration:none;' + '   display:block;' + '   float:left;' + '   margin-right:2px;' + '   padding: 0 2px;' + '   border:#666666 1px solid;' + '}' + 'A.LinkoolRotUnHover:link,A.LinkoolRotUnHover:hover,A.LinkoolRotUnHover:active,A.LinkoolRotUnHover:visited {' + '   color:black;' + '   background-color:#E1E1E1;' + '   border: 1px solid #CCCCCC;' + '   font-size:13px;' + '   line-height:13px;' + '   text-decoration:none;' + '   font-family: "黑体";' + '   display:block;' + '   float:left;' + '   margin-right:2px;' + '   padding: 0 2px;' + '   border:#666666 1px solid;' + '}' + '.lk_butterfly_top{' + '   width:315px;' + '   height:19px;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/butterfly/top.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/butterfly/top.png) no-repeat;';
alk_aM += '}' + '.lk_butterfly_bottom{' + '   width:315px;' + '   height:24px;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/butterfly/bottom.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/butterfly/bottom.png) no-repeat;';
alk_aM += '}' + '.lk_butterfly_left{' + '   width:22px;' + '   height:107px;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/butterfly/left.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/butterfly/left.png) no-repeat;';
alk_aM += '}' + '.lk_butterfly_center{' + '   width:272px;' + '   height:107px;' + '   background: url(http://www230.clickeye.cn/pic/butterfly/center.jpg) no-repeat;' + '}' + '.lk_butterfly_right{' + '   width:21px;' + '   height:107px;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/butterfly/right.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/butterfly/right.png) no-repeat;';
alk_aM += '}' + '.lk_yashili{' + '   width: 263px;' + '   height: 174px;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/client/yashili.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/client/yashili.png) no-repeat;';
alk_aM += '}' + '.lk_summer{' + '   width:200px;' + '   height:260px;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/summer/bkg.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/summer/bkg.png) no-repeat;';
alk_aM += '}' + '' + '.lk_tipbox{position:absolute;top:0px;left:0px;width:247px;text-align:left;background: url(http://www230.clickeye.cn/pic/chengcai2/bg.gif);height: 153px;}' + '.lk_23_title{position:absolute;top:35px;left:10px;height:20px;width:228px;font-weight:bold;}' + 'A.lk_23_title_a{font-size:14px;font-weight:bold;color:#000FFF;text-decoration:none;}' + 'A.lk_23_title_a:link, A.lk_23_title_a:active, A.lk_23_title_a:visited {color:#000FFF;background-color:transparent;}' + 'A.lk_23_title_a:hover { text-decoration:underline;color:#000FFF;}' + '.lk_23_contact{position:absolute;top:52px;left:10px;height:20px;width:228px;font-size:14px;font-weight:bold;}' + '.lk_23_contact span{color:#FF5A00;}' + '.lk_23_content_2{position:absolute;top:70px;left:10px;height:43px;width:228px}' + '.lk_23_content{position:absolute;top:71px;left:85px;height:63px;width:154px}' + 'A.lk_23_content_a {font-size:12px;color:#000000;line-height:16px;text-decoration:none;}' + 'A.lk_23_content_a:link,A.lk_23_conten:active,A.lk_23_conten:visited{color:black;background-color:white}' + 'A.lk_23_content_a:hover { text-decoration:underline;color:black;background-color:white}' + '.lk_23_pic{position: absolute;width:69px;height:63px;top:71px;left:10px;cursor:pointer}' + '.lk_23_more{position: absolute;width:78px;top:136px;left: 161px;height:16px;text-align:right;}' + 'A.lk_23_more_a{font-size:12px;color:#0000FF;}' + 'A.lk_23_more_a:link,A.lk_23_more_a:active,A.lk_23_more_a:visited{color:#0000FF;background-color:transparent;}' + 'A.lk_23_more_a:hover{text-decoration:underline;color:#0000FF;}' + '.lk_23_rot{position: absolute;width:145px;top:136px;left: 10px;height:16px}' + 'A.lk_23_rot_a{background-color:#CCCCCC;font-size:10px;line-height:12px;text-decoration:none;display:block;float:left;margin-right:2px;padding: 0 2px;border:#666666 1px solid;}' + 'A.lk_23_rot_a_u{background-color:#FFFFFF;font-size:10px;line-height:12px;text-decoration:none;display:block;float:left;margin-right:2px;padding: 0 2px;border:#666666 1px solid;}' + '.lk_23_text{width:228px;height:20px;top:121px;left:10px;position: absolute;font-size:12px;font-weight:bold;}' + '' + '' + '.lk_25_linkoolWindow{width:192px;text-align: left;}' + '.lk_25_title{' + '   position:absolute;top:0px;left:0px;height:23px;width:192px;cursor:pointer;';
if (lk_dE)alk_aM += 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=http://www230.clickeye.cn/pic/flv/top.png, sizingmethod="scale");'; else alk_aM += 'background: url(http://www230.clickeye.cn/pic/flv/top.png);';
alk_aM += '}' + '.lk_25_flv{position:absolute;top:23px;left:0px;}';
alk_aM.alk_aA();
alk_bc[2] = '<div class="LinkoolWindow">' + '   <div class="lk_butterfly_top"></div>   ' + '   <table width="315" height="107" border="0" cellpadding="0" cellspacing="0">' + '      <tr>' + '         <td><div class="lk_butterfly_left"></div></td>         ' + '         <td><div class="lk_butterfly_center">' + '             <a href="http://www.clickeye.cn" target="_blank" style="cursor:pointer"><div style="width:272px;height:17px"></div></a>' + '            <LinkoolFlatPanel/>' + '            <LinkoolFlatPanelButterfly/>' + '            <table width="272px" height="90px" border="0" cellpadding="0" cellspacing="0" >' + '              <tr>' + '               <td align="center" valign="middle" width="35%" onclick="lk_at(event, \'{link}\',0, 0)"><img width="0px"  height="0px" src="__image__" style="cursor:pointer" onload="lk_ao(this, 80, 80)"/> </td>' + '               <td align="left" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">' + '                  <tr >' + '                    <td onclick="lk_at(event, \'{link}\',0, 0)" ><a href="#" onclick="return false" class="DYText1_image_desp">{title} </a> </td>' + '                  </tr>' + '                  <tr>' + '                    <td valign="top" height="60px" class="AdsDescription_image" onclick="lk_at(event, \'{link}\',0, 0)" style="cursor:pointer;"> {description} </td>' + '                  </tr>' + '                  <tr>' + '                    <td align="right" valign="bottom" > ' + '                       <a href="#" class="LinkoolDefault" onclick="lk_at(event, \'{link}\',0, 0);return false">详细内容</a>&nbsp;&nbsp;</td>' + '                  </tr>' + '               </table></td>' + '              </tr>' + '           </table>' + '            <EndofLinkoolFlatPanelButterfly/>' + '            <EndofLinkoolFlatPanel/> ' + '         </div></td>' + '         <td><div class="lk_butterfly_right"></div></td>' + '      </tr>' + '  </table>' + '   <div class="lk_butterfly_bottom"></div>' + '</div>';
alk_bc[14] = '<div class="LinkoolWindow2">' + '<LinkoolFlatTitle/>' + '<div style="position:absolute;top:0px;left:0px"><a href="http://www.clickeye.cn" target="_blank"><DIV class="LinkoolFlatTitleFlash"></DIV></a></div>' + '<EndofLinkoolFlatTitle/>   ' + '' + '<LinkoolFlatPanel/>' + '   <LinkoolFlatPanelFlash/>' + '   ' + '   <div class="LinkoolFlash"  style="position:absolute;height:163px;top:31px;left:0px"></div>' + '      <div style="position:absolute;top:31px;left:12px">' + '         <embed style="z-index: 0;" flashvars="url=__image__" src="http://www230.clickeye.cn/client/swfplayer.swf" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent" height="163" width="192"/>' + '      </div>' + '      <button style="position:absolute; width:180px; height:15px; z-index:1000; left: 27px; top: 30px;  border: 0px none #000000;cursor:pointer;background:url(http://localhost/no.pic);" onclick="lk_at(event, \'{link}\',0, 0,true,0)"></button>' + '      <button style="position:absolute; width:195px; height:125px; z-index:1000; left: 12px; top: 45px;  border: 0px none #000000;cursor:pointer;background:url(http://localhost/no.pic);" onclick="lk_at(event, \'{link}\',0, 0,true,0)">' + '</button>' + '   <div style="position:absolute;top:194px;left:0px"><a href="http://www.clickeye.cn" target="_blank" ><DIV class="LinkoolFlatFooterFlash14" ></DIV></a></div>   ' + '<div style="height:212px"></div>' + '   <EndofLinkoolFlatPanelFlash/>   ' + '   <LinkoolFlatPanelImage/>' + '   <div class="LinkoolFlash"  onclick="lk_at(event, \'{link}\',0, 0,true)" style="position:absolute;top:31px;left:0px;">' + '      <div style="position:absolute;top:60px;left:60px;font-size:12px;width:80px">加载图片中...</div>' + '      <div style="position:absolute;top:0px;left:11px;width:192px;height:144px"><img height="0px" width="0px" src="__image__" onload="lk_ao(this, 192, 144)"/></div>' + '   </div>' + '   <div style="position:absolute;top:175px;left:0px"><a href="http://www.clickeye.cn" target="_blank" ><DIV class="LinkoolFlatFooterFlash" ></DIV></a></div>   ' + '<div style="height:192px"></div>' + '   <EndofLinkoolFlatPanelImage/>   ' + '<EndofLinkoolFlatPanel/>   ' + '</div>';
alk_bc[16] = '<div class="LinkoolWindow">' + '   <LinkoolFlatTitle/>' + '   <a href="http://www.clickeye.cn" target="_blank"><DIV id=LinkoolFlatTitle class="LinkoolFlatTitle"></DIV></a>' + '   <EndofLinkoolFlatTitle/>' + '   ' + '   <div class="lk_iTxt_image_desp" style="height:115px"></div>' + '   <LinkoolFlatPanel/>' + '   <LinkoolFlatPanelImageDesp/>' + '   <div style="position:absolute;left:11px;top:80px;font-size:12px">加载图片...</div>' + '   <div style="position:absolute;left:10px;top:48px;width:80px;height:80px;cursor:pointer" onclick="lk_at(event, \'{link}\',0, 0)"><img width="0px"  height="0px" src="__image__" onload="lk_ao(this, 80, 80)"/></div>' + '   <div style="position:absolute;left:100px;top:38px;width:135px;height:14px;overflow:hidden"><a href="#" class="DYText1_image_desp"  onclick="lk_at(event, \'{link}\',0, 0);return false;" >{title}</a></div>' + '   <div style="position:absolute;left:100px;top:56px;width:135px;height:70px;overflow:hidden" class="AdsDescription_image" ><a href="#" class="LinkoolAdsDesp" onclick="lk_at(event, \'{link}\',0, 0);return false;">{description}</a></div>' + '   <div style="position:absolute;left:185px;top:132px;width:50px;height:14x"><a href="#" class="LinkoolDefault" onclick="lk_at(event, \'{link}\',0, 0);return false">详细内容</a></div>         ' + '   <EndofLinkoolFlatPanelImageDesp/>' + '   <EndofLinkoolFlatPanel/>   ' + '   ' + '   <LinkoolFlatFooter/>' + '   <a href="http://www.clickeye.cn" target="_blank"><DIV class="LinkoolFlatFooter">   </DIV></a>' + '   <EndofLinkoolFlatFooter/>   ' + '</div>';
alk_bc[17] = '<div class="LinkoolWindow">' + '   <div class="lk_yashili"></div>' + '   <div style="position:absolute;top:0px;left:0px"><a href="http://www.clickeye.cn" target="_blank" style="cursor:pointer"><div style="height:30px;width:80px"></div></a></div>' + '   <div style="position:absolute;top:0px;left:80px;height:30px;width:50px"></div>' + '   <div style="position:absolute;top:0px;left:130px;height:30px;width:70px;cursor:pointer" onClick="lk_at(event, \'{link}\',0, 0,true)"></div>' + '   <LinkoolFlatPanel/>         ' + '   <LinkoolFlatPanelFlash/>         ' + '   <div style="position:absolute; top:34px; left:14px;">' + '      <embed style="z-index: 0;" flashvars="url=__image__" src="http://www230.clickeye.cn/client/npreload.swf" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent" height="138px" width="184px"/>' + '      <button style="position:absolute; width:171px; height:15px; z-index:1000; left: 15px; top: 0px;  border: 0px none #000000;cursor:pointer;background:url(http://localhost/no.pic);" onclick="lk_at(event, \'{link}\',0, 0,true,0)"></button><button style="position:absolute; width:184px; height:125px; z-index:1000; left: 0px; top: 15px;  border: 0px none #000000;cursor:pointer;background:url(http://localhost/no.pic);" onclick="lk_at(event, \'{link}\',0, 0,true,0)">' + '</button>' + '  </div>         ' + '   <EndofLinkoolFlatPanelFlash/>   ' + '   <EndofLinkoolFlatPanel/>' + '</div>';
alk_bc[18] = '<div class="LinkoolWindow">' + '   <LinkoolFlatTitle/>' + '   <div><a href="http://www.clickeye.cn" target="_blank"><DIV id=LinkoolFlatTitle class="LinkoolFlatTitle"></DIV></a></div>' + '   <EndofLinkoolFlatTitle/>' + '   ' + '   <div class="lk_iTxt">' + '      <LinkoolFlatPanel/>' + '      <LinkoolFlatPanelDesp/>' + '      <div class="flatPanel">   ' + '         <div class="lk_flatPanelDespTitle" onclick="lk_at(event, \'{link}\',0, 0)">' + '            <a href="#" onclick="return false;" class="DYText1"  onmouseover="this.style.background=\'url({iconurl}) no-repeat scroll 8px -16px\'"  onmouseout="this.style.background=\'url({iconurl}) no-repeat scroll 8px 0px\'" style="background:url({iconurl}) no-repeat scroll 8px 0px">{title}   </a>' + '         </div>' + '         <div class="AdsDescription" onclick="lk_at(event, \'{link}\',0, 0)">' + '            {description}' + '         </div>' + '      </div>' + '      <EndofLinkoolFlatPanelDesp/>' + '      <LinkoolFlatPanelNoDesp/>' + '      <div class="flatPanel">' + '         <div class="lk_flatPanelDespTitle" onclick="lk_at(event, \'{link}\',0, 0)" >' + '            <a href="#" onclick="return false;" class="DYText1"  onmouseover="this.style.background=\'url({iconurl}) no-repeat scroll 8px -16px\'"  onmouseout="this.style.background=\'url({iconurl}) no-repeat scroll 8px 0px\'" style="background:url({iconurl}) no-repeat scroll 8px 0px">{title}   </a>' + '         </div>' + '      </div>' + '      <EndofLinkoolFlatPanelNoDesp/>' + '      <EndofLinkoolFlatPanel/>' + '   </div>      ' + '   ' + '   <LinkoolMoreInfo/>' + '   <div class="MoreInfo"><a href="{moreurl}" class="MoreText" target="_blank">更多关于“{keyword}”的资讯&gt;&gt;</a>&nbsp;&nbsp;</div>' + '   <EndofLinkoolMoreInfo/>' + '   ' + '   <LinkoolRot/>' + '   <div  class="lk_linkoolRot">      ' + '      <LinkoolRotIcon/>' + '      <LinkoolRotIconUnHover/>' + '      <a onmouseover="alk_c({rotnum})" class="LinkoolRotUnHover" onclick="return false;" href="#">{rotnum}</a>' + '      <EndofLinkoolRotIconUnHover/>' + '      <LinkoolRotIconHover/>' + '      <a class="LinkoolRotHover" onclick="return false;" href="#">{rotnum}</a>' + '      <EndofLinkoolRotIconHover/>' + '      <EndofLinkoolRotIcon/>' + '   </div>' + '   <EndofLinkoolRot/>' + '   ' + '   <LinkoolFlatFooter/>' + '   <div><a href="http://www.clickeye.cn" target="_blank"><DIV class="LinkoolFlatFooterRot">   </DIV></a></div>' + '   <EndofLinkoolFlatFooter/>   ' + '</div>';
alk_bc[19] = '<div class="LinkoolWindow">' + '   <div>' + '   <LinkoolFlatPanel/>' + '      <LinkoolFlatPanelFlash/>' + '      <div class="LinkoolPanelFlash">' + '         <embed style="z-index: 0;" src="__image__" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent" height="144" width="192"/>' + '         <div style="position:absolute; width:180px; height:15px; z-index:1000; left: 27px; top:0px;  border: 0px none #000000;cursor:pointer;background:url(http://localhost/no.pic);" onclick="lk_at(event, \'{link}\',0, 0,true,0)"></div>' + '      <div style="position:absolute; width:195px; height:130px; z-index:1000; left: 12px; top: 15px;  border: 0px none #000000;cursor:pointer;background:url(http://localhost/no.pic);" onclick="lk_at(event, \'{link}\',0, 0,true,0)">' + '</div>   ' + '      </div>' + '      <EndofLinkoolFlatPanelFlash/>   ' + '      <LinkoolFlatPanelImage/>' + '      <div onclick="lk_at(event, \'{link}\',0, 0,true)">' + '      <div style="width:192px;height:144px"><img height="144px" width="192px" src="__image__" /></div>' + '      </div>   ' + '      <EndofLinkoolFlatPanelImage/>' + '      <LinkoolFlatPanel/>   ' + '   <EndofLinkoolFlatPanel/>   ' + '   </div>' + '</div>';
alk_bc[21] = '<div class="LinkoolWindow">' + '   <LinkoolFlatTitle/>' + '   <a href="http://www.clickeye.cn" target="_blank"><DIV id=LinkoolFlatTitle class="LinkoolFlatTitle"></DIV></a>' + '   <EndofLinkoolFlatTitle/>' + '   ' + '   <div class="lk_iTxt_image_desp"></div>' + '   <LinkoolFlatPanel/>' + '   <LinkoolFlatPanelImageDesp/>' + '   <div style="position:absolute;left:11px;top:80px;font-size:12px">加载图片...</div>' + '   <div style="position:absolute;left:11px;top:38px;width:96px;height:120px;cursor:pointer" onclick="lk_at(event, \'{link}\',0, 0)"><img width="0px"  height="0px" src="__image__" onload="lk_ao(this, 84, 120)"/></div>' + '   <div style="position:absolute;left:110px;top:38px;width:125px;height:14px;overflow:hidden"><a href="#" class="DYText1_image_desp"  onclick="lk_at(event, \'{link}\',0, 0);return false;" >{title}</a></div>' + '   <div style="position:absolute;left:110px;top:56px;width:125px;height:80px;overflow:hidden" class="AdsDescription_image" ><a href="#" class="LinkoolAdsDesp" onclick="lk_at(event, \'{link}\',0, 0);return false;">{description}</a></div>' + '   <div style="position:absolute;left:185px;top:145px;width:50px;height:14x"><a href="#" class="LinkoolDefault" onclick="lk_at(event, \'{link}\',0, 0);return false">立即抢购</a></div>         ' + '   <EndofLinkoolFlatPanelImageDesp/>' + '   <EndofLinkoolFlatPanel/>   ' + '   ' + '   <LinkoolFlatFooter/>' + '   <a href="http://www.clickeye.cn" target="_blank"><DIV class="LinkoolFlatFooter">   </DIV></a>' + '   <EndofLinkoolFlatFooter/>   ' + '</div>';
alk_bc[22] = '<div class="LinkoolWindow">' + '   <div class="lk_summer"></div>' + '   <a href="http://www.clickeye.cn" target="_blank" style="position:absolute;top:0px;height:0px;cursor:pointer"><div style="width:80px;height:80px"></div></a>' + '   <LinkoolFlatPanel/>' + '         ' + '      <LinkoolFlatPanelImage/>' + '         <div style="position:absolute;top:67px;left:34px;height:181px;width:135px;cursor:pointer" onclick="lk_at(event, \'{link}\',0, 0,true)">' + '            <img height="0px" width="0px" src="__image__" onload="lk_ao(this, 135, 181)"/>' + '         </div>' + '      <EndofLinkoolFlatPanelImage/>   ' + '   <EndofLinkoolFlatPanel/>' + '</div>';
alk_bc[23] = '<div class="LinkoolWindow3">' + '   <div class="lk_tipbox"></div>' + '   <div style="position:absolute;top:0px;left:12px;"><a href="http://www.clickeye.cn" target="_blank"><div style="height:28px;width:60px;cursor:pointer"></div></a></div>' + '   <div style="position:absolute;top:0px;left:180px;"><a href="http://www.clickeye.cn" target="_blank"><div style="height:28px;width:60px;cursor:pointer"></div></a></div>' + '   <LinkoolFlatPanel/>' + '   <LinkoolFlatPanelImageDesp/>   ' + '   <div class="lk_23_title"><a href="#" onClick="lk_at(event, \'{link}\',0, 0,true);return false;" class="lk_23_title_a">{title}</a></div>' + '   <div class="lk_23_contact"><span>免费</span>咨询电话:<span>{tel}</span></div>' + '   <div class="lk_23_content"><a class="lk_23_content_a" href="#" onClick="lk_at(event, \'{link}\',0, 0,true);return false;">{description}</a></div>' + '   <div class="lk_23_pic" onClick="lk_at(event, \'{link}\',0, 0,true);return false;"><img height="0px" width="0px" src="__image__" onload="lk_ao(this, 63, 69)"/></div>   ' + '   <EndofLinkoolFlatPanelImageDesp/>' + '   ' + '   <LinkoolFlatPanelDespNoImage/>' + '   <div class="lk_23_title"><a href="#" onClick="lk_at(event, \'{link}\',0, 0,true);return false;" class="lk_23_title_a">{title}</a></div>' + '   <div class="lk_23_contact"><span>免费</span>咨询电话:<span>{tel}</span></div>' + '   <div class="lk_23_content_2"><a class="lk_23_content_a" href="#" onClick="lk_at(event, \'{link}\',0, 0,true);return false;">{description}</a></div>' + '   <div class="lk_23_text">通过成才网网上报名不满意全额退费！</div>' + '   <EndofLinkoolFlatPanelDespNoImage/>' + '   ' + '   <EndofLinkoolFlatPanel/>' + '   ' + '   <LinkoolMoreInfo/>' + '   <div class="lk_23_more"><a class="lk_23_more_a" href="#" onClick="lk_at(event, \'{link}\',0, 0,true);return false;">了解详情&gt;&gt;</a></div>' + '   <EndofLinkoolMoreInfo/>' + '   ' + '   <LinkoolRot/>' + '   <div class="lk_23_rot">' + '      <LinkoolRotIcon/>' + '      <LinkoolRotIconUnHover/>' + '      <a class="lk_23_rot_a_u" href="#" onmouseover="alk_c({rotnum})" onClick="return false;">{rotnum}</a> ' + '      <EndofLinkoolRotIconUnHover/>' + '      <LinkoolRotIconHover/>' + '      <a class="lk_23_rot_a" href="#" onClick="return false;">{rotnum}</a>' + '      <EndofLinkoolRotIconHover/>' + '      <EndofLinkoolRotIcon/>' + '   </div>' + '   <EndofLinkoolRot/>' + '</div>';
alk_bc[25] = '<div class="lk_25_linkoolwindow">' + '   <div style="height:187px;width:192px"></div>' + '   <a href="http://www.clickeye.cn" target="_blank"><div class="lk_25_title"></div></a>' + '   <div class="lk_25_flv">' + '      <LinkoolFlatPanel/>' + '      <LinkoolFlatPanelFlv/>' + '      <embed allowscriptaccess="never"    src="http://www230.clickeye.cn/client/flvplayer.swf?file=__image__&autostart=true&vol=false" width="192" height="164" type="application/x-shockwave-flash"  wmode="transparent" quality="high"> </embed>      ' + '      <EndofLinkoolFlatPanelFlv/>' + '      <EndofLinkoolFlatPanel/>      ' + '      <button style="position:absolute; width:192px; height:144px; z-index:1000; left: 0px; top: 0px;  border: 0px none #000000;cursor:pointer;background:url(http://localhost/no.pic);" onclick="lk_at(event, \'{link}\',0, 0,true,0)"></button>' + '   </div>' + '</div>';