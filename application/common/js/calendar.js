/*
 * Compressed by JSA(www.xidea.org)
 */
Calendar=function(A,B,_,D){this.activeDiv=null;this.currentDateEl=null;this.checkDisabled=null;this.timeout=null;this.onSelected=_||null;this.onClose=D||null;this.dragging=false;this.hidden=false;this.minYear=1970;this.maxYear=2050;this.dateFormat=Calendar._TT["DEF_DATE_FORMAT"];this.ttDateFormat=Calendar._TT["TT_DATE_FO