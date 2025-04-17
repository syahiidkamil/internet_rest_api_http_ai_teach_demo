// API base URL - change this to match your Spring Boot server
const API_BASE_URL = 'http://localhost:8080/api';

// Storage for auth token
let authToken = '';
let currentUser = null;

// DOM elements
document.addEventListener('DOMContentLoaded', () => {
    // Initialize token status
    updateTokenStatus();

    // Register form handler
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const username = document.getElementById('registerUsername').value;
        const password = document.getElementById('registerPassword').value;
        const name = document.getElementById('registerName').value;
        const email = document.getElementById('registerEmail').value;
        
        register(username, password, name, email);
    });

    // Login form handler
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const username = document.getElementById('loginUsername').value;
        const password = document.getElementById('loginPassword').value;
        
        login(username, password);
    });

    // Logout button handler
    document.getElementById('logoutButton').addEventListener('click', function() {
        logout();
    });

    // Product form handler
    document.getElementById('productForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const name = document.getElementById('productName').value;
        const description = document.getElementById('productDescription').value;
        const price = document.getElementById('productPrice').value;
        const stock = document.getElementById('productStock').value;
        const imageUrl = document.getElementById('productImageUrl').value;
        
        createProduct(name, description, price, stock, imageUrl);
    });

    // Get all products button
    document.getElementById('getAllProductsButton').addEventListener('click', function() {
        getAllProducts();
    });

    // Search products button
    document.getElementById('searchProductButton').addEventListener('click', function() {
        const searchTerm = document.getElementById('productSearchTerm').value;
        searchProducts(searchTerm);
    });

    // AI prompt form handler
    document.getElementById('aiPromptForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const prompt = document.getElementById('aiPrompt').value;
        
        generateAiContent(prompt);
    });

    // Secure AI prompt form handler
    document.getElementById('secureAiPromptForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const prompt = document.getElementById('secureAiPrompt').value;
        
        generateSecureAiContent(prompt);
    });
});

// Update token status display
function updateTokenStatus() {
    const tokenStatus = document.getElementById('token-status');
    
    if (authToken) {
        tokenStatus.innerHTML = `Logged in as: <strong>${currentUser.username}</strong>`;
        tokenStatus.classList.add('logged-in');
        tokenStatus.classList.remove('logged-out');
        document.getElementById('authSection').classList.add('hidden');
        document.getElementById('loggedInSection').classList.remove('hidden');
    } else {
        tokenStatus.innerHTML = 'Not logged in';
        tokenStatus.classList.add('logged-out');
        tokenStatus.classList.remove('logged-in');
        document.getElementById('authSection').classList.remove('hidden');
        document.getElementById('loggedInSection').classList.add('hidden');
    }
}

// Registration function
async function register(username, password, name, email) {
    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username,
                password,
                name,
                email
            })
        });
        
        const data = await response.json();
        document.getElementById('authResponse').textContent = JSON.stringify(data, null, 2);
        
        if (response.ok) {
            // Auto-login after successful registration
            login(username, password);
        }
    } catch (error) {
        document.getElementById('authResponse').textContent = `Error: ${error.message}`;
    }
}

// Login function
async function login(username, password) {
    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username,
                password
            })
        });
        
        const data = await response.json();
        document.getElementById('authResponse').textContent = JSON.stringify(data, null, 2);
        
        if (response.ok && data.data) {
            authToken = data.data.token;
            currentUser = {
                username: data.data.username,
                name: data.data.name,
                role: data.data.role
            };
            updateTokenStatus();
        }
    } catch (error) {
        document.getElementById('authResponse').textContent = `Error: ${error.message}`;
    }
}

// Logout function
function logout() {
    authToken = '';
    currentUser = null;
    updateTokenStatus();
    document.getElementById('authResponse').textContent = 'Logged out successfully';
}

// Get all products
async function getAllProducts() {
    try {
        const response = await fetch(`${API_BASE_URL}/products`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });
        
        const data = await response.json();
        document.getElementById('productsResponse').textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        document.getElementById('productsResponse').textContent = `Error: ${error.message}`;
    }
}

// Search products by name
async function searchProducts(searchTerm) {
    try {
        const response = await fetch(`${API_BASE_URL}/products/search?name=${encodeURIComponent(searchTerm)}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });
        
        const data = await response.json();
        document.getElementById('productsResponse').textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        document.getElementById('productsResponse').textContent = `Error: ${error.message}`;
    }
}

// Create a new product
async function createProduct(name, description, price, stock, imageUrl) {
    try {
        const response = await fetch(`${API_BASE_URL}/products`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name,
                description,
                price: parseFloat(price),
                stock: parseInt(stock),
                imageUrl
            })
        });
        
        const data = await response.json();
        document.getElementById('productsResponse').textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        document.getElementById('productsResponse').textContent = `Error: ${error.message}`;
    }
}

// Generate AI content (public endpoint)
async function generateAiContent(prompt) {
    try {
        const response = await fetch(`${API_BASE_URL}/ai/generate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                prompt
            })
        });
        
        const data = await response.json();
        document.getElementById('aiResponse').textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        document.getElementById('aiResponse').textContent = `Error: ${error.message}`;
    }
}

// Generate AI content (secured endpoint)
async function generateSecureAiContent(prompt) {
    if (!authToken) {
        document.getElementById('secureAiResponse').textContent = 'Error: Authentication required. Please log in first.';
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/ai/generate-secure`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${authToken}`
            },
            body: JSON.stringify({
                prompt
            })
        });
        
        const data = await response.json();
        document.getElementById('secureAiResponse').textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        document.getElementById('secureAiResponse').textContent = `Error: ${error.message}`;
    }
}
