<?php
if (! defined('BASEPATH'))
    exit('No direct script access allowed');

require (APPPATH . 'libraries/REST_Controller.php');

class Api extends REST_Controller {

    public function adidas_post() {
        $this->load->library('twitter');
        $this->twitter->post($this->post('id'), "New highlight of the match");
    }

     public function updatescore_post() {
        $this->load->model('Match_Model');
        $match = $this->Match_Model->getMatchByID($this->post('_id'));
        if ($match) {
            $match->setHomeScore($this->post('scoreLocal'));
            $match->setAwayScore($this->post('scoreVisitant'));
           
            $$this->Match_Model->updateMatch($match);
            $data['status'] = 200;
            $this->response($data);
        } else {
            $data['status'] = 404;
            $this->response($data);
        }
    }
}

/* End of file api.php */
/* Location: ./application/controllers/api.php */
