/* setup for when the page rendering is done and ready to bind things like events */
$(document).ready(function(){
    // menu events
    $('#menu').bind('mouseover mouseout', function() {
        $('#menuitems').toggle();
    });

    $('#menu').bind('click', function(){
        window.location.replace(basepath);
    });

    //toggle div content on click of header divs
    $('[id$="header"]').bind('click', function() {
        //they follow a pattern of ${id}header -> ${id}
        //so if you strip off 'header' from the header div, the content div will be that id
        var id = this.id.substr(0, this.id.length - 6);
        $('[id="' + id + '"]').toggle();
    });

    //database page events
    $('#jndiselection').bind('change', function(){
        $('#jndiblock').show();
        $('#connstringblock').hide();
    });

    $('#connstringselection').bind('change', function(){
        $('#connstringblock').show();
        $('#jndiblock').hide();
    });

    $('#connstringselection').attr('checked', false);
    $('#jndiselection').attr('checked', false);

    //jms topic events
    $('#topicselection').bind('change', function(){
        $('#topicblock').show();
        $('#queueblock').hide();
    });

    $('#queueselection').bind('change', function(){
        $('#queueblock').show();
        $('#topicblock').hide();
    });

    $('#topicselection').attr('checked', false);
    $('#queueselection').attr('checked', false);

    //jms action events
    $('#jmsreceive').bind('change', function(){
        $('#receiveblock').show();
        $('#sendblock').hide();
        $('#clearblock').hide();
    });

    $('#jmssend').bind('change', function(){
        $('#sendblock').show();
        $('#receiveblock').hide();
        $('#clearblock').hide();
    });

    $('#jmsclear').bind('change', function(){
        $('#clearblock').show();
        $('#receiveblock').hide();
        $('#sendblock').hide();
    });

    $('#jmsreceive').attr('checked', false);
    $('#jmssend').attr('checked', false);
    $('#jmsclear').attr('checked', false);
    
    //format action events
    $('#formatbutton').bind('click', function(){
        var selection = $('input[name=formatselection]:checked').val();
        var str = $('textarea[id=input]').val();

        try {
            if(selection === 'cssformat') {
                str = vkbeautify.css(str, 3);
            } else if(selection === 'jsonformat') {
                str = vkbeautify.json(str, 3);
            } else if(selection === 'sqlformat') {
                str = vkbeautify.sql(str, 3);
            } else if(selection === 'xmlformat') {
                str = vkbeautify.xml(str, 3);
            }

            //kinda sloppy, could be better
            $("#data").text(str);
            $("#data").show();
            $("#dataheader").show();

            $("#error").text("");
            $("#error").hide();
            $("#errorheader").hide();
        } catch (err) {
            $("#data").text("No data");
            $("#data").show();
            $("#dataheader").show();

            $("#error").text(err.toString());
            $("#error").show();
            $("#errorheader").show();
        }
    });
});

/* function to set the welcome banner (it shares the data div) */
function welcome() {
    $("#data").html('<h2>Welcome to my awesome utility.  Prepare to utilize!</h2>');
}


/**
 * Format a string of text to have a certain line length, and break to new lines based on the
 * desired length.  Can optionally cut words longer than the max length apart (recommended; 
 * default true).
 * @param text the text to format
 * @param length the length a line should be
 * @param cut (optional) whether or not to cut words that extend past the line length by itself 
   (single word length > line length)  (defaults true)
 * @return formatted string
 */
var format = function(text, length, cut) {
    //default the input params
    text = text || '';
    length = length || 80;
    cut = (cut != false);   //it's only false when it is false!

    //setup regex and split apart
    var re = new RegExp('.{1,' + length + '}(\\n|\\r\\n|$)' + (cut ? '|.{' + length + '}|.+$' : '|\\S+?(\\s{1,2}|$)') + '|(\\n|\\r\\n)', 'g');
    var arr = text.match(re);
    var rval = '';

    //reconstruct the text based on the regex
    for(var i = 0; (arr != null && i < arr.length); ++i) {
        rval = rval.concat(arr[i].replace(/[\s]+$/, '')); //trim the right side, preserve left
        rval = rval.concat('\n');   //concat new newlines for new lines!
    }

    return rval;
};//format