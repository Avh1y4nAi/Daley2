// Charts UI - Simplified for UI display only

document.addEventListener('DOMContentLoaded', function () {
    // Common chart options
    const chartDefaults = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: { display: false },
            tooltip: {
                backgroundColor: '#262626',
                titleColor: '#fff',
                bodyColor: '#fff',
                bodyFont: { size: 14 },
                padding: 12,
                cornerRadius: 8
            }
        },
        scales: {
            x: { grid: { display: false } },
            y: {
                beginAtZero: true,
                grid: { color: 'rgba(0, 0, 0, 0.05)' }
            }
        }
    };

    // Helper function to create charts
    const createChart = (id, type, data, options = {}) => {
        const ctx = document.getElementById(id);
        if (!ctx) return null;

        // Create new chart with merged options
        return new Chart(ctx, {
            type,
            data,
            options: { ...chartDefaults, ...options }
        });
    };

    // Initialize charts if they exist in the DOM
    // These are simplified placeholder charts for UI display only

    // User Growth Chart
    const userGrowthChart = document.getElementById('userGrowthChart');
    if (userGrowthChart) {
        createChart('userGrowthChart', 'line', {
            labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
            datasets: [{
                label: 'New Users',
                data: [42, 55, 48, 60],
                borderColor: '#4f6df5',
                backgroundColor: 'rgba(79, 109, 245, 0.1)',
                borderWidth: 2,
                tension: 0.3,
                fill: true,
                pointBackgroundColor: '#4f6df5',
                pointBorderColor: '#fff',
                pointBorderWidth: 2,
                pointRadius: 4,
                pointHoverRadius: 6
            }]
        });
    }

    // Revenue Chart
    const revenueChart = document.getElementById('revenueChart');
    if (revenueChart) {
        createChart('revenueChart', 'bar', {
            labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
            datasets: [{
                label: 'Revenue ($)',
                data: [32000, 45000, 38000, 50000],
                backgroundColor: '#34d399',
                borderRadius: 6,
                barThickness: 'flex',
                maxBarThickness: 35
            }]
        }, {
            plugins: {
                tooltip: {
                    callbacks: {
                        label: context => '$' + context.raw.toLocaleString()
                    }
                }
            }
        });
    }

    // Payment Summary Chart
    const paymentChart = document.getElementById('paymentSummaryChart');
    if (paymentChart) {
        createChart('paymentSummaryChart', 'line', {
            labels: ['January', 'February', 'March', 'April', 'May', 'June'],
            datasets: [{
                label: 'Payment Amount ($)',
                data: [1200, 1500, 1800, 1200, 2500, 1800],
                borderColor: '#4f6df5',
                backgroundColor: 'rgba(79, 109, 245, 0.1)',
                borderWidth: 2,
                tension: 0.3,
                fill: true,
                pointBackgroundColor: '#4f6df5',
                pointBorderColor: '#fff',
                pointBorderWidth: 2,
                pointRadius: 4,
                pointHoverRadius: 6
            }]
        });
    }

    // Application Status Chart
    const applicationChart = document.getElementById('applicationStatusChart');
    if (applicationChart) {
        createChart('applicationStatusChart', 'doughnut', {
            labels: ['Approved', 'Pending', 'Rejected'],
            datasets: [{
                data: [40, 45, 15],
                backgroundColor: ['#34d399', '#fbbf24', '#f87171'],
                borderWidth: 0,
                borderRadius: 4,
                hoverOffset: 10
            }]
        }, {
            cutout: '65%',
            plugins: {
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        padding: 20,
                        usePointStyle: true,
                        pointStyle: 'circle'
                    }
                }
            }
        });
    }

    // Review Ratings Chart
    const reviewChart = document.getElementById('reviewRatingsChart');
    if (reviewChart) {
        createChart('reviewRatingsChart', 'bar', {
            labels: ['5 Stars', '4 Stars', '3 Stars', '2 Stars', '1 Star'],
            datasets: [{
                label: 'Number of Reviews',
                data: [45, 30, 15, 7, 3],
                backgroundColor: '#6366f1',
                borderRadius: 6,
                barThickness: 'flex',
                maxBarThickness: 35
            }]
        }, {
            indexAxis: 'y'
        });
    }
});
