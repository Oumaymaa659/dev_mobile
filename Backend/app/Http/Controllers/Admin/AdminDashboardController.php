<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Location;
use App\Models\Caftan;

class AdminDashboardController extends Controller
{
    public function index()
    {
        $stats = [
            'total_locations' => Location::count(),
            'en_attente'      => Location::where('statut', 'en_attente')->count(),
            'confirmees'      => Location::where('statut', 'confirmee')->count(),
            'total_caftans'   => Caftan::count(),
        ];

        return view('admin.dashboard', compact('stats'));
    }
}
