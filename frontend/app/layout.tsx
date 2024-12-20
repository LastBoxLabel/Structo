import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import './globals.css'
import { Navbar } from '../components/navbar'
import { Footer } from '../components/footer'
import React from "react";
import { Toaster } from 'sonner';

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Structo',
  description: 'A system for planning and visualization of IT projects',
  icons: {
    icon: '/favicon.ico',
  },
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="pt">
      <head>
      </head>
      <body className={inter.className}>
        <div className="flex flex-col min-h-screen">
          <Navbar />
          <main className="flex-grow">{children}</main>
          <Footer />
          <Toaster />
        </div>
      </body>
    </html>
  )
}

