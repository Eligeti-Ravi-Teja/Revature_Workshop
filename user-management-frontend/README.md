# User Management System - Frontend

A modern, responsive web application for managing users with a beautiful UI and comprehensive functionality.

## 🚀 Features

### Core Functionality
- **User CRUD Operations**: Create, Read, Update, Delete users
- **Search & Filter**: Advanced search capabilities with multiple filters
- **Statistics Dashboard**: Real-time user analytics
- **Responsive Design**: Works perfectly on desktop, tablet, and mobile

### New Features Added
- **Export Functionality**: Export user data to CSV format
- **Theme Toggle**: Switch between light and dark themes
- **Bulk Operations**: Select multiple users for bulk actions
  - Bulk delete
  - Bulk activate/deactivate
  - Select all functionality

### User Interface
- **Modern Design**: Clean, professional interface with smooth animations
- **Toast Notifications**: Real-time feedback for user actions
- **Form Validation**: Real-time validation with helpful error messages
- **Modal Dialogs**: Smooth editing experience
- **Tab Navigation**: Organized interface with clear sections

## 🛠️ Technology Stack

- **HTML5**: Semantic markup
- **CSS3**: Modern styling with Flexbox and Grid
- **JavaScript (ES6+)**: Vanilla JavaScript with modern features
- **Node.js**: Simple HTTP server for development

## 📁 Project Structure

```
user-management-frontend/
├── index.html          # Main application file
├── styles.css          # Complete styling with dark theme
├── script.js           # Application logic and functionality
├── server.js           # Development server
├── package.json        # Dependencies
└── README.md          # This file
```

## 🚀 Getting Started

### Prerequisites
- Node.js (v14 or higher)

### Installation & Running

1. **Navigate to the frontend directory**:
   ```bash
   cd user-management-frontend
   ```

2. **Start the development server**:
   ```bash
   node server.js
   ```

3. **Open your browser** and visit:
   ```
   http://localhost:8082
   ```

## 🎯 How to Use

### 1. Get Started Page
- Welcome screen with feature overview
- Click "Get Started" to enter the main application

### 2. Create User
- Fill in user details (email, name, phone, address, role)
- Real-time validation ensures data quality
- Click "Create User" to add to the system

### 3. Search & Filter
- **Quick Search**: Search across all fields
- **Advanced Search**: Search by specific fields (email, first name, last name)
- **Filter Options**: Filter by role or status
- **Export**: Download user data as CSV

### 4. User List
- View all users with detailed information
- **Bulk Operations**: Select multiple users for batch actions
- Individual actions: Edit, Activate/Deactivate, Delete

### 5. Statistics
- Real-time user statistics
- Total users, active users, admin users
- Refresh button to update data

### 6. Theme Toggle
- Click the moon/sun icon in the header
- Switch between light and dark themes
- Theme preference is saved locally

## 🎨 Features in Detail

### Export Functionality
- Click "Export Users" in the Search & Filter tab
- Downloads a CSV file with all user data
- Includes: ID, Email, Name, Phone, Address, Role, Status, Dates

### Bulk Operations
- **Select All**: Checkbox to select all users
- **Individual Selection**: Checkboxes for each user
- **Bulk Actions**:
  - Delete Selected: Remove multiple users
  - Activate Selected: Enable multiple users
  - Deactivate Selected: Disable multiple users

### Dark Theme
- Toggle between light and dark themes
- Persistent theme preference
- Smooth transitions between themes
- Optimized colors for both themes

### Form Validation
- **Email**: Must contain @ and .com
- **Phone**: Exactly 10 digits
- **Required Fields**: First name and last name
- **Real-time Feedback**: Immediate validation messages

## 📱 Responsive Design

The application is fully responsive and works on:
- **Desktop**: Full feature set with sidebar navigation
- **Tablet**: Optimized layout with collapsible sections
- **Mobile**: Touch-friendly interface with simplified navigation

## 🔧 Customization

### Adding New Fields
1. Update the HTML form in `index.html`
2. Add validation in `script.js` (validateForm function)
3. Update the createUserCard function to display the new field
4. Add CSS styling in `styles.css`

### Modifying Styles
- Main styles are in `styles.css`
- Dark theme styles are prefixed with `body.dark-theme`
- Responsive breakpoints at 768px and 480px

## 🐛 Troubleshooting

### Common Issues

1. **Port already in use**:
   ```bash
   pkill -f "node server.js"
   node server.js
   ```

2. **Theme not saving**: Check browser localStorage support

3. **Export not working**: Ensure browser allows downloads

## 📈 Future Enhancements

- User activity logging
- Advanced filtering (date ranges)
- User import functionality
- Data visualization charts
- User roles and permissions
- API integration with backend

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is open source and available under the MIT License.

---

**Happy User Management!** 🎉 