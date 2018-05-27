<head>
    <script src="https://static.opentok.com/v2/js/opentok.min.js"></script>

    <!-- Polyfill for fetch API so that we can fetch the sessionId and token in IE11 -->
    <script src="https://cdn.jsdelivr.net/npm/promise-polyfill@7/dist/polyfill.min.js" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fetch/2.0.3/fetch.min.js" charset="utf-8"></script>
</head>

<body>
	<input type="hidden" name="match" id="match" value="<?php echo $match;?>"/>
    <div id="videos">
        <div id="subscriber"></div>
    </div>

    <div id="adidasScoreboard">
        <img class="adidasCrest" src="https://www.competize.com/img/crests/ft_crest_0.png"/>
        <span class="adidasTeamName">RAD</span>
        
        <span id="adidasResult"><span id="homeGoals">0</span> - <span id="awayGoals">0</span></span>

        <span class="adidasTeamName">FCA</span>
        <img class="adidasCrest" src="https://www.competize.com/img/crests/ft_crest_1.png"/>
    </div>
    <div id="adidasBigScoreboard">
		<div>
    		<img class="adidasBigCrest" src="https://www.competize.com/img/crests/ft_crest_0.png"/>
        <span class="adidasBigTeamName">Real Adidas</span>
        
        <span id="adidasBigResult"><span id="homeGoalsBig">0</span> - <span id="awayGoalsBig">0</span></span>

        <span class="adidasBigTeamName">FC Adidas</span>
        <img class="adidasBigCrest" src="https://www.competize.com/img/crests/ft_crest_1.png"/>
		</div>
    </div>
    
    <script type='text/javascript' src='<?php echo base_url(); ?>js/adidas.js'></script>
</body>