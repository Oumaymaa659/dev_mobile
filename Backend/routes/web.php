<?php



use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Admin\AdminDashboardController;
use App\Http\Controllers\Admin\AdminCaftanController;
use App\Http\Controllers\Admin\AdminLocationController;
use App\Http\Controllers\AuthController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

// Rediriger la page d'accueil vers le login
Route::get('/', function () {
    return redirect()->route('login');
});

// Page de test du panier (avec auth)
Route::get('/test-panier', function () {
    return view('test-panier');
});

// Page de test mode invité (SANS auth)
Route::get('/test-mode-invite', function () {
    return view('test-mode-invite');
});

// Page de test mode invité SIMPLIFIÉ (panier local)
Route::get('/test-invite', function () {
    return view('test-mode-invite-simple');
});

// Routes d'authentification
Route::get('/login', [AuthController::class, 'showLogin'])->name('login');
Route::post('/login', [AuthController::class, 'login']);
Route::post('/logout', [AuthController::class, 'logout'])->name('logout');

// Routes Admin protégées
Route::prefix('admin')
    ->middleware(['auth', 'admin'])
    ->group(function () {

        Route::get('/dashboard', [AdminDashboardController::class, 'index'])
            ->name('admin.dashboard');

        Route::resource('caftans', AdminCaftanController::class)
            ->names('admin.caftans')
            ->except(['show', 'edit', 'update']);

        Route::get('/locations', [AdminLocationController::class, 'index'])
            ->name('admin.locations.index');

        Route::post('/locations/{id}/confirmer', [AdminLocationController::class, 'confirmer'])
            ->name('admin.locations.confirmer');

        Route::post('/locations/{id}/refuser', [AdminLocationController::class, 'refuser'])
            ->name('admin.locations.refuser');
});
