<?php
if (! defined('BASEPATH'))
    exit('No direct script access allowed');

class Match extends MY_Controller {

    public function __construct() {
        parent::__construct();
    }

    public function adidas($match) {
        $this->content['match'] = $match;
        $this->generatePreView();
    }
    
    public function getScoreboard($match) {
         $this->match = $this->Match_Model->getMatchByID($match);
         $data['homeScore'] = $this->match->getScoreLocal();
         $data['awayScore'] = $this->match->getScoreVisitant();
         
         echo json_encode($data);
     }
}