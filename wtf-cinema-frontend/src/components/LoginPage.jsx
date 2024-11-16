// src/components/LoginPage.jsx
import { useState } from 'react';
import { Link } from 'react-router-dom';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('https://proyecto-tic-1-grupo-1.onrender.com/api/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                window.location.href = '/home';
            } else {
                setError('Invalid credentials');
            }
        } catch (error) {
            console.error('Fetch error:', error);
            setError('Error during login');
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <div className="max-w-md w-full space-y-8 p-8 bg-white rounded-lg shadow">
                <div className="text-center">
                    <h2 className="text-3xl font-bold text-gray-900">WTF Cinema</h2>
                    <p className="mt-2 text-gray-600">Sign in to your account</p>
                </div>
                <form className="mt-8 space-y-6" onSubmit={handleLogin}>
                    {error && <div className="text-red-500 text-center">{error}</div>}
                    <div>
                        <input
                            type="text"
                            required
                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                            placeholder="Username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                    <div>
                        <input
                            type="password"
                            required
                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    <div>
                        <button
                            type="submit"
                            className="w-full py-2 px-4 border border-transparent rounded-md shadow-sm text-white bg-blue-600 hover:bg-blue-700"
                        >
                            Sign in
                        </button>
                    </div>
                </form>
                <div className="text-center">
                    <Link to="/register" className="text-blue-600 hover:text-blue-800">
                        Create new account
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;
