// Azure API Configuration
const API_BASE_URL = 'https://app-test-01-d4dpbwhgawcxg8ej.japanwest-01.azurewebsites.net/api/users';

// Global state
let users = [];
let selectedUsers = new Set();

// API Helper Functions
async function apiCall(endpoint, options = {}) {
    try {
        const url = endpoint.startsWith('http') ? endpoint : `${API_BASE_URL}${endpoint}`;
        console.log('API Call:', url, options);
        
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        console.log('API Response:', data);
        return data;
    } catch (error) {
        console.error('API Error:', error);
        showToast(`API Error: ${error.message}`, 'error');
        throw error;
    }
}

// Toastify Notification Function
function showToast(message, type = 'success') {
    const backgroundColor = type === 'success' ? '#28a745' : 
                          type === 'error' ? '#dc3545' : 
                          type === 'warning' ? '#ffc107' : '#667eea';
    
    Toastify({
        text: message,
        duration: 4000,
        gravity: "top",
        position: "right",
        backgroundColor: backgroundColor,
        color: "white",
        stopOnFocus: true,
        style: {
            borderRadius: "8px",
            fontSize: "14px",
            fontWeight: "600"
        }
    }).showToast();
}

// Navigation Functions
function showMainApp() {
    document.getElementById('getStartedPage').style.display = 'none';
    document.getElementById('mainApp').style.display = 'block';
    showToast('Welcome to User Management System!', 'success');
    loadUsers();
    updateStatistics();
}

function showGetStartedPage() {
    document.getElementById('mainApp').style.display = 'none';
    document.getElementById('getStartedPage').style.display = 'flex';
    showToast('Returned to start page', 'success');
}

// Theme toggle functionality
function toggleTheme() {
    const body = document.body;
    const themeToggle = document.getElementById('themeToggle');
    
    if (body.classList.contains('dark-theme')) {
        body.classList.remove('dark-theme');
        themeToggle.textContent = '🌙';
        localStorage.setItem('theme', 'light');
        showToast('Switched to light theme', 'success');
    } else {
        body.classList.add('dark-theme');
        themeToggle.textContent = '☀️';
        localStorage.setItem('theme', 'dark');
        showToast('Switched to dark theme', 'success');
    }
}

// Load saved theme
function loadTheme() {
    const savedTheme = localStorage.getItem('theme');
    const themeToggle = document.getElementById('themeToggle');
    
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-theme');
        if (themeToggle) {
            themeToggle.textContent = '☀️';
        }
    }
}

// Tab Management
function switchTab(tabName) {
    // Hide all tab contents
    const tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(content => {
        content.classList.remove('active');
    });
    
    // Remove active class from all tab buttons
    const tabButtons = document.querySelectorAll('.tab-button');
    tabButtons.forEach(button => {
        button.classList.remove('active');
    });
    
    // Show selected tab content
    const selectedTab = document.getElementById(tabName);
    if (selectedTab) {
        selectedTab.classList.add('active');
    }
    
    // Add active class to selected tab button
    const selectedButton = document.querySelector(`[data-tab="${tabName}"]`);
    if (selectedButton) {
        selectedButton.classList.add('active');
    }
}

// Validation Functions
function validateEmail(email) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
}

function validateForm(formData) {
    const errors = [];
    
    // Email validation
    if (!validateEmail(formData.email)) {
        errors.push('Email must be in valid format');
    }
    
    // Required fields
    if (!formData.firstName.trim()) {
        errors.push('First name is required');
    }
    
    if (!formData.lastName.trim()) {
        errors.push('Last name is required');
    }
    
    return errors;
}

// Utility Functions
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleString();
}

function createUserCard(user) {
    return `
        <div class="user-item" data-user-id="${user.id}">
            <div class="user-header">
                <div style="display: flex; align-items: center; gap: 10px;">
                    <input type="checkbox" class="user-checkbox" value="${user.id}" onchange="toggleUserSelection('${user.id}')">
                    <div>
                        <div class="user-name">${user.firstName} ${user.lastName}</div>
                        <div class="user-email">${user.email}</div>
                    </div>
                </div>
                <span class="user-status ${user.active ? 'status-active' : 'status-inactive'}">
                    ${user.active ? 'Active' : 'Inactive'}
                </span>
            </div>
            <div class="user-details">
                <div class="user-detail">
                    <strong>Role:</strong> ${user.role || 'N/A'}
                </div>
                <div class="user-detail">
                    <strong>Phone:</strong> ${user.phoneNumber || 'N/A'}
                </div>
                <div class="user-detail">
                    <strong>Address:</strong> ${user.address || 'N/A'}
                </div>
                <div class="user-detail">
                    <strong>Created:</strong> ${formatDate(user.createdAt)}
                </div>
            </div>
            <div class="user-actions">
                <button onclick="editUser('${user.id}')" class="btn btn-secondary">Edit</button>
                <button onclick="toggleUserStatus('${user.id}', ${!user.active})" class="btn ${user.active ? 'btn-danger' : 'btn-success'}">
                    ${user.active ? 'Deactivate' : 'Activate'}
                </button>
                <button onclick="deleteUser('${user.id}')" class="btn btn-danger">Delete</button>
            </div>
        </div>
    `;
}

// CRUD Operations - Using Azure API
async function createUser(userData) {
    try {
        // Validate form data
        const validationErrors = validateForm(userData);
        if (validationErrors.length > 0) {
            showToast(`Validation errors: ${validationErrors.join(', ')}`, 'error');
            return null;
        }
        
        const response = await apiCall('', {
            method: 'POST',
            body: JSON.stringify(userData)
        });
        
        showToast('User created successfully!', 'success');
        document.getElementById('createUserForm').reset();
        await loadUsers();
        updateStatistics();
        
        // Switch to User List tab to show the new user
        switchTab('users');
        
        return response;
    } catch (error) {
        showToast('Failed to create user', 'error');
        return null;
    }
}

async function getAllUsers() {
    try {
        const response = await apiCall('');
        return response;
    } catch (error) {
        showToast('Failed to fetch users', 'error');
        return [];
    }
}

async function getUserById(id) {
    try {
        const response = await apiCall(`/${id}`);
        return response;
    } catch (error) {
        showToast('Failed to fetch user', 'error');
        return null;
    }
}

async function updateUser(id, userData) {
    try {
        const validationErrors = validateForm(userData);
        if (validationErrors.length > 0) {
            showToast(`Validation errors: ${validationErrors.join(', ')}`, 'error');
            return null;
        }
        
        const response = await apiCall(`/${id}`, {
            method: 'PUT',
            body: JSON.stringify(userData)
        });
        
        showToast('User updated successfully!', 'success');
        await loadUsers();
        updateStatistics();
        return response;
    } catch (error) {
        showToast('Failed to update user', 'error');
        throw error;
    }
}

async function deleteUser(id) {
    if (!confirm('Are you sure you want to delete this user?')) {
        return;
    }
    
    try {
        await apiCall(`/${id}`, {
            method: 'DELETE'
        });
        
        showToast('User deleted successfully!', 'success');
        await loadUsers();
        updateStatistics();
    } catch (error) {
        showToast('Failed to delete user', 'error');
    }
}

// Search and Filter Functions - Using Azure API
async function quickSearch() {
    const searchTerm = document.getElementById('quickSearch').value.trim().toLowerCase();
    if (!searchTerm) {
        await loadUsers();
        showToast('Please enter a search term', 'warning');
        return;
    }
    
    try {
        // For quick search, we'll fetch all users and filter client-side
        const allUsers = await getAllUsers();
        const filteredUsers = allUsers.filter(user => 
            user.firstName.toLowerCase().includes(searchTerm) ||
            user.lastName.toLowerCase().includes(searchTerm) ||
            user.email.toLowerCase().includes(searchTerm) ||
            (user.role && user.role.toLowerCase().includes(searchTerm)) ||
            (user.phoneNumber && user.phoneNumber.includes(searchTerm)) ||
            (user.address && user.address.toLowerCase().includes(searchTerm))
        );
        
        if (filteredUsers.length === 0) {
            showToast('No users found matching your search', 'warning');
        } else {
            showToast(`Found ${filteredUsers.length} user(s)`, 'success');
        }
        
        displayUsers(filteredUsers);
        switchTab('users');
    } catch (error) {
        showToast('Search failed', 'error');
    }
}

async function searchByEmail() {
    const email = document.getElementById('searchEmail').value.trim();
    if (!email) {
        showToast('Please enter an email to search', 'warning');
        return;
    }
    
    try {
        const response = await apiCall(`/email/${email}`);
        const users = Array.isArray(response) ? response : [response];
        
        if (users.length === 0) {
            showToast('No users found with that email', 'warning');
        } else {
            showToast(`Found ${users.length} user(s)`, 'success');
        }
        
        displayUsers(users);
        switchTab('users');
    } catch (error) {
        showToast('No users found with that email', 'warning');
    }
}

async function searchByFirstName() {
    const firstName = document.getElementById('searchFirstName').value.trim();
    if (!firstName) {
        showToast('Please enter a first name to search', 'warning');
        return;
    }
    
    try {
        const response = await apiCall(`/search/firstname/${firstName}`);
        const users = Array.isArray(response) ? response : [response];
        
        if (users.length === 0) {
            showToast('No users found with that first name', 'warning');
        } else {
            showToast(`Found ${users.length} user(s)`, 'success');
        }
        
        displayUsers(users);
        switchTab('users');
    } catch (error) {
        showToast('No users found with that first name', 'warning');
    }
}

async function searchByLastName() {
    const lastName = document.getElementById('searchLastName').value.trim();
    if (!lastName) {
        showToast('Please enter a last name to search', 'warning');
        return;
    }
    
    try {
        const response = await apiCall(`/search/lastname/${lastName}`);
        const users = Array.isArray(response) ? response : [response];
        
        if (users.length === 0) {
            showToast('No users found with that last name', 'warning');
        } else {
            showToast(`Found ${users.length} user(s)`, 'success');
        }
        
        displayUsers(users);
        switchTab('users');
    } catch (error) {
        showToast('No users found with that last name', 'warning');
    }
}

async function filterByRole() {
    const role = document.getElementById('filterRole').value;
    if (!role) {
        await loadUsers();
        showToast('Showing all users', 'success');
    } else {
        try {
            const response = await apiCall(`/role/${role}`);
            const users = Array.isArray(response) ? response : [response];
            showToast(`Found ${users.length} user(s) with role: ${role}`, 'success');
            displayUsers(users);
        } catch (error) {
            showToast('Failed to filter by role', 'error');
        }
    }
    
    switchTab('users');
}

async function filterByStatus() {
    const status = document.getElementById('filterStatus').value;
    if (!status) {
        await loadUsers();
        showToast('Showing all users', 'success');
    } else {
        try {
            if (status === 'active') {
                const response = await apiCall('/active');
                const users = Array.isArray(response) ? response : [response];
                showToast(`Found ${users.length} active user(s)`, 'success');
                displayUsers(users);
            } else {
                // For inactive users, we'll need to fetch all and filter
                const allUsers = await getAllUsers();
                const inactiveUsers = allUsers.filter(user => !user.active);
                showToast(`Found ${inactiveUsers.length} inactive user(s)`, 'success');
                displayUsers(inactiveUsers);
            }
        } catch (error) {
            showToast('Failed to filter by status', 'error');
        }
    }
    
    switchTab('users');
}

async function getActiveUsers() {
    try {
        const response = await apiCall('/active');
        const users = Array.isArray(response) ? response : [response];
        displayUsers(users);
        showToast(`Showing ${users.length} active user(s)`, 'success');
        switchTab('users');
    } catch (error) {
        showToast('Failed to fetch active users', 'error');
    }
}

async function clearSearch() {
    // Clear all search inputs
    document.getElementById('quickSearch').value = '';
    document.getElementById('searchEmail').value = '';
    document.getElementById('searchFirstName').value = '';
    document.getElementById('searchLastName').value = '';
    document.getElementById('filterRole').value = '';
    document.getElementById('filterStatus').value = '';
    
    // Show all users
    await loadUsers();
    showToast('All filters cleared', 'success');
    switchTab('users');
}

async function toggleUserStatus(id, active) {
    try {
        await apiCall(`/${id}/status?active=${active}`, {
            method: 'PATCH'
        });
        
        const statusText = active ? 'activated' : 'deactivated';
        showToast(`User ${statusText} successfully!`, 'success');
        await loadUsers();
        updateStatistics();
    } catch (error) {
        showToast('Failed to update user status', 'error');
    }
}

// Statistics Functions
async function updateStatistics() {
    try {
        const allUsers = await getAllUsers();
        const totalUsers = allUsers.length;
        const activeUsers = allUsers.filter(user => user.active).length;
        const adminUsers = allUsers.filter(user => user.role === 'ADMIN').length;
        
        document.getElementById('totalUsers').textContent = totalUsers;
        document.getElementById('activeUsers').textContent = activeUsers;
        document.getElementById('adminUsers').textContent = adminUsers;
    } catch (error) {
        console.error('Failed to update statistics:', error);
    }
}

async function loadStatistics() {
    await updateStatistics();
    showToast('Statistics updated!', 'success');
}

// UI Functions
function displayUsers(usersToDisplay) {
    const userList = document.getElementById('userList');
    
    if (usersToDisplay.length === 0) {
        userList.innerHTML = `
            <div class="empty-state">
                <h3>No users found</h3>
                <p>Try adjusting your search criteria or create a new user.</p>
            </div>
        `;
        return;
    }
    
    userList.innerHTML = usersToDisplay.map(user => createUserCard(user)).join('');
}

async function loadUsers() {
    try {
        users = await getAllUsers();
        displayUsers(users);
    } catch (error) {
        showToast('Failed to load users', 'error');
        displayUsers([]);
    }
}

// Modal Functions
function openEditModal(user) {
    const modal = document.getElementById('editModal');
    
    // Populate form fields
    document.getElementById('editUserId').value = user.id;
    document.getElementById('editEmail').value = user.email;
    document.getElementById('editFirstName').value = user.firstName;
    document.getElementById('editLastName').value = user.lastName;
    document.getElementById('editPhoneNumber').value = user.phoneNumber || '';
    document.getElementById('editAddress').value = user.address || '';
    document.getElementById('editRole').value = user.role || '';
    document.getElementById('editActive').value = user.active.toString();
    
    modal.style.display = 'block';
}

function closeEditModal() {
    const modal = document.getElementById('editModal');
    modal.style.display = 'none';
}

async function editUser(id) {
    try {
        const user = await getUserById(id);
        if (!user) {
            showToast('User not found', 'error');
            return;
        }
        openEditModal(user);
    } catch (error) {
        showToast('Failed to load user details', 'error');
    }
}

// Export functionality
function exportUsers() {
    try {
        const csvContent = generateCSV(users);
        const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
        const link = document.createElement('a');
        const url = URL.createObjectURL(blob);
        link.setAttribute('href', url);
        link.setAttribute('download', `users_export_${new Date().toISOString().split('T')[0]}.csv`);
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        showToast('Users exported successfully!', 'success');
    } catch (error) {
        showToast('Failed to export users', 'error');
    }
}

function generateCSV(users) {
    const headers = ['ID', 'Email', 'First Name', 'Last Name', 'Phone Number', 'Address', 'Role', 'Status', 'Created At', 'Updated At'];
    const csvRows = [headers.join(',')];
    
    users.forEach(user => {
        const row = [
            user.id,
            `"${user.email}"`,
            `"${user.firstName}"`,
            `"${user.lastName}"`,
            `"${user.phoneNumber || ''}"`,
            `"${user.address || ''}"`,
            user.role || '',
            user.active ? 'Active' : 'Inactive',
            user.createdAt ? new Date(user.createdAt).toLocaleDateString() : '',
            user.updatedAt ? new Date(user.updatedAt).toLocaleDateString() : ''
        ];
        csvRows.push(row.join(','));
    });
    
    return csvRows.join('\n');
}

// Bulk operations
function toggleSelectAll() {
    const selectAllCheckbox = document.getElementById('selectAll');
    const userCheckboxes = document.querySelectorAll('.user-checkbox');
    const bulkButtons = document.querySelectorAll('.bulk-buttons button');
    
    if (selectAllCheckbox.checked) {
        userCheckboxes.forEach(checkbox => {
            checkbox.checked = true;
            selectedUsers.add(checkbox.value);
        });
        bulkButtons.forEach(btn => btn.style.display = 'inline-block');
    } else {
        userCheckboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
        selectedUsers.clear();
        bulkButtons.forEach(btn => btn.style.display = 'none');
    }
}

function toggleUserSelection(userId) {
    if (selectedUsers.has(userId)) {
        selectedUsers.delete(userId);
    } else {
        selectedUsers.add(userId);
    }
    
    const userCheckboxes = document.querySelectorAll('.user-checkbox');
    const selectAllCheckbox = document.getElementById('selectAll');
    const bulkButtons = document.querySelectorAll('.bulk-buttons button');
    
    // Update select all checkbox
    const checkedCount = userCheckboxes.length;
    const selectedCount = selectedUsers.size;
    selectAllCheckbox.checked = selectedCount === checkedCount;
    selectAllCheckbox.indeterminate = selectedCount > 0 && selectedCount < checkedCount;
    
    // Show/hide bulk buttons
    if (selectedUsers.size > 0) {
        bulkButtons.forEach(btn => btn.style.display = 'inline-block');
    } else {
        bulkButtons.forEach(btn => btn.style.display = 'none');
    }
}

async function bulkDelete() {
    if (selectedUsers.size === 0) {
        showToast('Please select users to delete', 'warning');
        return;
    }
    
    if (confirm(`Are you sure you want to delete ${selectedUsers.size} user(s)?`)) {
        const deletePromises = Array.from(selectedUsers).map(userId => deleteUser(userId));
        await Promise.all(deletePromises);
        selectedUsers.clear();
        document.getElementById('selectAll').checked = false;
        document.querySelectorAll('.bulk-buttons button').forEach(btn => btn.style.display = 'none');
        showToast(`${selectedUsers.size} users deleted successfully`, 'success');
    }
}

async function bulkActivate() {
    if (selectedUsers.size === 0) {
        showToast('Please select users to activate', 'warning');
        return;
    }
    
    const activatePromises = Array.from(selectedUsers).map(userId => toggleUserStatus(userId, true));
    await Promise.all(activatePromises);
    showToast(`${selectedUsers.size} users activated successfully`, 'success');
}

async function bulkDeactivate() {
    if (selectedUsers.size === 0) {
        showToast('Please select users to deactivate', 'warning');
        return;
    }
    
    const deactivatePromises = Array.from(selectedUsers).map(userId => toggleUserStatus(userId, false));
    await Promise.all(deactivatePromises);
    showToast(`${selectedUsers.size} users deactivated successfully`, 'success');
}

// Event Listeners
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM loaded, initializing application...');
    
    // Load saved theme
    loadTheme();
    
    // Get Started button
    const getStartedBtn = document.getElementById('getStartedBtn');
    if (getStartedBtn) {
        getStartedBtn.addEventListener('click', function() {
            console.log('Get Started button clicked');
            showMainApp();
        });
    } else {
        console.error('Get Started button not found');
    }
    
    // Back to Start button
    const backToStartBtn = document.getElementById('backToStartBtn');
    if (backToStartBtn) {
        backToStartBtn.addEventListener('click', showGetStartedPage);
    }
    
    // Theme toggle button
    const themeToggleBtn = document.getElementById('themeToggle');
    if (themeToggleBtn) {
        themeToggleBtn.addEventListener('click', toggleTheme);
    }
    
    // Tab functionality
    const tabButtons = document.querySelectorAll('.tab-button');
    tabButtons.forEach(button => {
        button.addEventListener('click', function() {
            const tabName = this.getAttribute('data-tab');
            switchTab(tabName);
        });
    });
    
    // Create user form
    const createUserForm = document.getElementById('createUserForm');
    if (createUserForm) {
        createUserForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            const userData = {
                email: formData.get('email'),
                firstName: formData.get('firstName'),
                lastName: formData.get('lastName'),
                phoneNumber: formData.get('phoneNumber'),
                address: formData.get('address'),
                role: formData.get('role')
            };
            
            await createUser(userData);
        });
    }
    
    // Edit user form
    const editUserForm = document.getElementById('editUserForm');
    if (editUserForm) {
        editUserForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const userId = document.getElementById('editUserId').value;
            const formData = new FormData(this);
            const userData = {
                email: formData.get('email'),
                firstName: formData.get('firstName'),
                lastName: formData.get('lastName'),
                phoneNumber: formData.get('phoneNumber'),
                address: formData.get('address'),
                role: formData.get('role')
            };
            
            await updateUser(userId, userData);
            closeEditModal();
        });
    }
    
    // Modal close button
    const closeBtn = document.querySelector('.close');
    if (closeBtn) {
        closeBtn.addEventListener('click', closeEditModal);
    }
    
    // Close modal when clicking outside
    window.addEventListener('click', function(e) {
        const modal = document.getElementById('editModal');
        if (e.target === modal) {
            closeEditModal();
        }
    });
    
    // Real-time validation for phone number
    const phoneInputs = document.querySelectorAll('input[type="tel"]');
    phoneInputs.forEach(input => {
        input.addEventListener('input', function() {
            // Remove non-numeric characters
            this.value = this.value.replace(/\D/g, '');
            
            // Limit to 10 digits
            if (this.value.length > 10) {
                this.value = this.value.slice(0, 10);
            }
        });
    });
    
    // Enter key for quick search
    const quickSearchInput = document.getElementById('quickSearch');
    if (quickSearchInput) {
        quickSearchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                quickSearch();
            }
        });
    }
    
    console.log('Application initialized successfully');
    showToast('User Management System loaded successfully!', 'success');
});

// Global functions for button onclick handlers
window.searchByEmail = searchByEmail;
window.searchByFirstName = searchByFirstName;
window.searchByLastName = searchByLastName;
window.quickSearch = quickSearch;
window.filterByRole = filterByRole;
window.filterByStatus = filterByStatus;
window.getActiveUsers = getActiveUsers;
window.getAllUsers = getAllUsers;
window.clearSearch = clearSearch;
window.editUser = editUser;
window.deleteUser = deleteUser;
window.toggleUserStatus = toggleUserStatus;
window.loadStatistics = loadStatistics;
window.exportUsers = exportUsers;
window.toggleSelectAll = toggleSelectAll;
window.toggleUserSelection = toggleUserSelection;
window.bulkDelete = bulkDelete;
window.bulkActivate = bulkActivate;
window.bulkDeactivate = bulkDeactivate; 