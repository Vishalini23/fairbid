// ==========================================
// CONFIGURATION
// ==========================================
const BASE_URL = 'http://localhost:8080/fairbid-backend-1.0-SNAPSHOT';

// ==========================================
// HELPER FETCH FUNCTION
// ==========================================
async function sendPostRequest(endpoint, payload) {
    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            throw new Error(`Server returned status code: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error(`API Error at ${endpoint}:`, error);
        return { success: false };
    }
}

// ==========================================
// 1. FARMER AUTHENTICATION & REGISTRATION
// ==========================================
async function handleFarmerLogin(event) {
    event.preventDefault();
    const data = {
        farmerId: document.getElementById('loginId').value.trim(),
        password: document.getElementById('loginPassword').value.trim()
    };

    const response = await sendPostRequest('/api/auth/farmer-login', data);
    if (response && response.success) {
        sessionStorage.setItem("loggedInFarmer", JSON.stringify({ id: data.farmerId }));
        window.location.href = 'farmer_dashboard.html';
    } else {
        const loginErr = document.getElementById('loginError');
        if (loginErr) loginErr.textContent = "Invalid Farmer ID or password.";
    }
}

async function handleFarmerRegister(event) {
    event.preventDefault();
    const data = {
        fullName: document.getElementById('regFullName').value.trim(),
        farmerId: document.getElementById('regFarmerId').value.trim(),
        state: document.getElementById('regState').value,
        primaryCrop: document.getElementById('regCrop').value,
        password: document.getElementById('regPassword').value.trim()
    };

    const response = await sendPostRequest('/api/auth/farmer-register', data);
    const regMsg = document.getElementById('regMessage');
    
    if (response && response.success) {
        if (regMsg) {
            regMsg.className = "success";
            regMsg.textContent = "Registration successful! Redirecting to login...";
        }
        setTimeout(() => {
            document.getElementById("registerForm").reset();
            if (typeof switchTab === 'function') switchTab('login');
            document.getElementById("loginId").value = data.farmerId;
        }, 1500);
    } else {
        if (regMsg) {
            regMsg.className = "error";
            regMsg.textContent = "Registration failed. This ID/Mobile may already exist.";
        }
    }
}

// ==========================================
// 2. BUYER AUTHENTICATION & REGISTRATION
// ==========================================
async function handleLogin(event) {
    event.preventDefault();
    const data = {
        email: document.getElementById('loginEmail').value.trim(),
        password: document.getElementById('loginPassword').value.trim()
    };

    const response = await sendPostRequest('/api/auth/buyer-login', data);
    const errorMsg = document.getElementById('loginError');

    if (response && response.success) {
        if (errorMsg) errorMsg.style.display = 'none';
        sessionStorage.setItem("loggedInBuyer", JSON.stringify({ email: data.email }));
        window.location.href = "buyer_dashboard.html";
    } else {
        if (errorMsg) errorMsg.style.display = 'block';
    }
}

async function handleRegister(event) {
    event.preventDefault();
    const data = {
        name: document.getElementById('regName').value.trim(),
        email: document.getElementById('regEmail').value.trim(),
        password: document.getElementById('regPassword').value.trim(),
        category: document.getElementById('buyerCategory').value,
        docNumber: document.getElementById('docInput') ? document.getElementById('docInput').value.trim() : ""
    };

    const response = await sendPostRequest('/api/auth/buyer-register', data);
    if (response && response.success) {
        alert("Registration successful! You can now log in.");
        document.getElementById('registerForm').reset();
        if (typeof switchTab === 'function') switchTab('login');
    } else {
        alert("Registration failed or email already exists.");
    }
}

// ==========================================
// 3. CROP LISTING
// ==========================================
async function addProduct(event) {
    event.preventDefault();
    const loggedInFarmer = JSON.parse(sessionStorage.getItem("loggedInFarmer"));
    const farmerId = loggedInFarmer ? loggedInFarmer.id : "F101";

    const data = {
        farmerId: farmerId,
        cropName: document.getElementById("cropName").value,
        quantity: parseInt(document.getElementById("quantity").value),
        minPrice: parseFloat(document.getElementById("minPrice").value),
        location: document.getElementById("location").value.trim()
    };

    const response = await sendPostRequest('/api/crops', data);
    if (response && response.success) {
        alert("Product listed successfully!");
        document.getElementById("productForm").reset();
    } else {
        alert("Failed to save crop to database.");
    }
}

// ==========================================
// 4. BIDDING SYSTEM
// ==========================================
async function submitBid(event) {
    event.preventDefault();
    const urlParams = new URLSearchParams(window.location.search);
    const cropId = parseInt(urlParams.get('id')) || 101;
    
    const loggedInBuyer = JSON.parse(sessionStorage.getItem("loggedInBuyer"));
    const buyerEmail = loggedInBuyer ? loggedInBuyer.email : "buyer@example.com";

    const data = {
        cropId: cropId,
        buyerEmail: buyerEmail,
        bidAmount: parseFloat(document.getElementById('bidAmount').value)
    };

    const response = await sendPostRequest('/api/bids', data);
    if (response && response.success) {
        alert("Bid successfully recorded!");
    } else {
        alert("Bid submission failed.");
    }
}