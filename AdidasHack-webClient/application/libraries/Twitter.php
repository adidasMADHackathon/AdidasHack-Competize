<?php

use Abraham\TwitterOAuth\TwitterOAuth;

class Twitter {

    const ARCHIVES_SERVER = "https://adidashack.herokuapp.com/archive/";
    const CLIPS_SERVER = "/var/www/html2/competize/data/";
    const WATERMARK_PATH = "/var/www/html2/competize/data/";
    const CONSUMER_KEY = "XXXX";
    const CONSUMER_SECRET = "XXXX";
    const TOKEN = "XXXX";
    const TOKEN_SECRET = "XXXX";
        
    function __construct($params = array()) {
        $this->ci = get_instance();
    }

    public function post($idVideo, $message) {
        $input = Twitter::ARCHIVES_SERVER . $idVideo . "/view";
        $output = Twitter::CLIPS_SERVER . $idVideo . ".mp4";
        $arrOutput = array();
        $watermark= '-i \''. Twitter::WATERMARK_PATH . ' -filter_complex "overlay=10:10"';   
        $command = '/usr/local/bin/ffmpeg/ffmpeg-4.0-64bit-static/ffmpeg -sseof -20 -i ' . $input . ' ' . $watermark . ' \'' . $output . '\' > /dev/null &';
        
        exec($command, $arrOutput );
        
        $connection = new TwitterOAuth(Twitter::CONSUMER_KEY, Twitter::CONSUMER_SECRET, Twitter::TOKEN, $Twitter::TOKEN_SECRET);
        
        $connection->post("statuses/update", ["status" => $message . " https://www.competize.com/video/index/" . $idVideo . ".mp4"]);
    }
}
