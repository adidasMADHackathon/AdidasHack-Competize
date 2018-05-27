var SAMPLE_SERVER_BASE_URL = "https://adidashack.herokuapp.com/";

function handleError(error) {
  if (error) {
    console.error(error);
  }
}

initStreaming();

function initStreaming() {	
	if (SAMPLE_SERVER_BASE_URL) {
	  fetch(SAMPLE_SERVER_BASE_URL + 'room/adidas').then(function fetch(res) {
	    return res.json();
	  }).then(function fetchJson(json) {
	    apiKey = json.apiKey;
	    sessionId = json.sessionId;
	    token = json.token;

	    initializeSession();
	  }).catch(function catchErr(error) {
	    handleError(error);
	    alert('Failed to get opentok sessionId and token. Make sure you have updated the config.js file.');
	  });
	}
}

function listenNews() {
	match = $("#match").val();
	url = CI.base_url + CI.language + "/match/getScoreboard/" + match;
	
	$.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        success: function(data) {
        		oldHomeGoals = $("#homeGoals").text();
        		oldAwayGoals = $("#awayGoals").text();
        		        		
        		console.log(oldHomeGoals);
        		console.log(oldAwayGoals);
        		if (oldHomeGoals > 0 && oldAwayGoals > 0 && (data.homeScore != oldHomeGoals || data.awayScore != oldAwayGoals)) {
            		$("#adidasBigScoreboard").show();
            		myVar = setTimeout(hideBigScoreboard, 5000);
        		}
        		$("#homeGoals").text(data.homeScore);
        		$("#awayGoals").text(data.awayScore);
        		$("#homeGoalsBig").text(data.homeScore);
        		$("#awayGoalsBig").text(data.awayScore);
         },
        error: function(message, status) {
        		console.log(status+": "+message.responseText);
        }
      });
}

function hideBigScoreboard() {
	$("#adidasBigScoreboard").hide();
}

function initializeSession() {
  showFullViewMatch();
  var myVar = setInterval(listenNews, 2000);
  var session = OT.initSession(apiKey, sessionId);

  // Subscribe to a newly created stream
  session.on('streamCreated', function streamCreated(event) {
    var subscriberOptions = {
      insertMode: 'append',
      width: '100%',
      height: '100%',
      showControls: false
    };
    session.subscribe(event.stream, 'subscriber', subscriberOptions, handleError);
  });

  session.on('sessionDisconnected', function sessionDisconnected(event) {
    console.log('You were disconnected from the session.', event.reason);
  });

  // initialize the publisher
  var publisherOptions = {
    insertMode: 'append',
    width: '100%',
    height: '100%'
  };

  // Connect to the session
  session.connect(token, function callback(error) {
    if (error) {
      handleError(error);
    } else {
      // If the connection is successful, publish the publisher to the session
      session.publish(publisher, handleError);
    }
  });
}

function showFullViewMatch() {
	$("#main-header").hide();
	$("#content").removeClass("main-content");
	$("#regular-footer").hide();
}
