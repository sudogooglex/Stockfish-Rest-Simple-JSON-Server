// Static variables
// POST
var URLJSONPOST = '/SR/wr/post/';
var USERSTARTLENGTHPOST = 52;
var USERENDLENGTH = 14;
var USERDATAMINLENGTHPOST = USERSTARTLENGTHPOST + USERENDLENGTH;
var REGEXMOVES = /((\\\"moves\\\" : \\\")(((([1-8abcdefgh]{4}[BNQRbnqr]{0,1}\s)+([1-8abcdefgh]{4}[BNQRbnqr]{0,1}))|([1-8abcdefgh]{4}[BNQRbnqr]{0,1})|(^$)){0,1})(\\\"))/g;
//             var myRegexp = /((\\\"moves\\\" : \\\")(((([1-8abcdefgh]{4}\s)+([1-8abcdefgh]{4}))|([1-8abcdefgh]{4})|(^$)){0,1})(\\\"))/g;
// /(((\\\"moves\\\" : \\\"))((([1-8abcdefgh]{4}[BNQRbnqr]{0,1}\s)+([1-8abcdefgh]{4}[BNQRbnqr]{0,1}))|([1-8abcdefgh]{4}[BNQRbnqr]{0,1})|())(\\",))/g





// BOT
var URLJSONBOT = '/SR/wr/bot/';
var USERSTARTLENGTHBOT = 51;
var USERDATAMINLENGTHBOT = USERSTARTLENGTHBOT + USERENDLENGTH;





// OPTIMIZED
var URL_JSON_OPTIMIZED = '/SR/wr/optimized/';
var USERSTARTLENGTHOPTIMIZED = 0;
var USERDATAMINLENGTHOPTIMIZED = USERSTARTLENGTHOPTIMIZED + USERENDLENGTH;
var REGEX_MOVES_BOT = /((\\\"moves\\\" : \\\")(((([1-8abcdefgh]{4}[BNQRbnqr]{0,1}\s)+([1-8abcdefgh]{4}[BNQRbnqr]{0,1}))|([1-8abcdefgh]{4}[BNQRbnqr]{0,1})|(^$)){0,1})(\\\"))/g;

